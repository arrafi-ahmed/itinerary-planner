<template>
  <v-col cols="12">
    <v-hover v-slot="{ isHovering, props }">
      <v-sheet class="d-flex position-relative" rounded v-bind="props">
        <div class="position-absolute right-0 bg-grey-lighten-2">
          <v-dialog v-model="editDialog" scrollable>
            <template v-slot:activator="{ props: propsDialog }">
              <v-btn
                v-if="isHovering"
                density="compact"
                icon="mdi-pencil"
                size="small"
                v-bind="propsDialog"
                variant="text"
                @click="handleEditHotel(placeId)"
              ></v-btn>
            </template>

            <v-row justify="center">
              <v-col cols="6">
                <v-card>
                  <v-card-title>
                    <span class="text-h5">Edit Hotel</span>
                  </v-card-title>
                  <v-card-text>
                    <v-form
                      ref="form"
                      v-model="isFormValid"
                      fast-fail
                      @submit.prevent
                    >
                      <v-text-field
                        v-model="editedHotelInit.name"
                        :rules="[(v) => !!v || 'Name is required!']"
                        label="Name"
                        variant="underlined"
                      ></v-text-field>

                      <v-text-field
                        v-model="editedHotelInit.startTime"
                        :rules="[(v) => !!v || 'Start Time is required!']"
                        label="Start Time"
                        type="date"
                        variant="underlined"
                      ></v-text-field>

                      <v-text-field
                        v-model="editedHotelInit.endTime"
                        :rules="[
                          (v) => !!v || 'End Time is required!',
                          (v) =>
                            v > editedHotelInit.startTime ||
                            'End Time must be greater than Start Time',
                        ]"
                        label="End Time"
                        type="date"
                        variant="underlined"
                      ></v-text-field>

                      <v-text-field
                        v-model="editedHotelInit.address"
                        label="Address"
                        required
                        variant="underlined"
                      ></v-text-field>

                      <v-text-field
                        v-model="editedHotelInit.cost"
                        label="Cost"
                        prefix="$"
                        type="number"
                        variant="underlined"
                      ></v-text-field>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn type="submit" @click="handleEditHotelSubmit(placeId)"
                    >Edit
                    </v-btn>
                    <v-btn @click="editDialog = false"> Close</v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
          </v-dialog>

          <v-dialog v-model="deleteDialog" scrollable>
            <template v-slot:activator="{ props: propsDialog }">
              <v-btn
                v-if="isHovering"
                color="red-lighten-1"
                density="compact"
                icon="mdi-close"
                size="small"
                v-bind="propsDialog"
                variant="text"
                @click="deleteDialog = true"
              ></v-btn>
            </template>

            <v-row justify="center">
              <v-col cols="6">
                <v-card>
                  <v-card-title>
                    <span class="text-h5">Delete Hotel</span>
                  </v-card-title>
                  <v-card-text> Are you sure you want to delete?</v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn @click="handleDeleteHotelSubmit(id, placeId)"
                    >Delete
                    </v-btn>
                    <v-btn @click="deleteDialog = false"> Close</v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
          </v-dialog>
        </div>
        <v-icon
          class="align-self-center px-2"
          color="red"
          icon="mdi-bed"
          size="25"
        ></v-icon>
        <div class="flex-column pa-2">
          <v-badge :content="`\$${cost}`" color="info" inline></v-badge>
          <h3 class="d-inline-block">{{ name }}</h3>
          <v-icon>mdi-circle-small</v-icon>
          {{ address }}
          <v-icon>mdi-circle-small</v-icon>
          <strong>Checkin:</strong> {{ formatDate(startTime) }}
          <v-icon>mdi-circle-small</v-icon>
          <strong>Checkout:</strong> {{ formatDate(endTime) }}
        </div>
      </v-sheet>
    </v-hover>
  </v-col>
</template>

<script setup>
import {formatDate} from "@/util";
import {reactive, ref} from "vue";
import {useStore} from "vuex";
import {useRoute} from "vue-router";

const props = defineProps([
  "placeId",
  "id",
  "name",
  "address",
  "startTime",
  "endTime",
  "cost",
]);
const store = useStore();
const route = useRoute();
const editDialog = ref(false);
const deleteDialog = ref(false);
const editedHotelInit = reactive({});
const form = ref(null);
const isFormValid = ref(true);
const initialCost = ref(0);

const handleEditHotel = (placeId) => {
  const placeIndex = store.getters["trip/getPlaceIndex"](placeId);
  Object.assign(
    editedHotelInit,
    store.state.trip.trip.places[placeIndex].hotel
  );
  initialCost.value = editedHotelInit.cost || 0;
};

const handleEditHotelSubmit = async (placeId) => {
  await form.value.validate();
  if (!isFormValid.value) return;

  const request = {
    tripId: route.params.tripId,
    placeId: placeId,
    hotelId: editedHotelInit.id,
    initialCost: initialCost.value,
    ...editedHotelInit,
  };
  store
    .dispatch("trip/editHotel", request)
    .then((res) => {
      editDialog.value = false;
      store.dispatch("setSnackbar", {
        message: "Hotel edited successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error editing hotel!",
        color: "error",
      });
    });
};

const handleDeleteHotelSubmit = (id, placeId) => {
  const request = {
    hotelId: id,
    placeId: placeId,
    cost: props.cost,
  };
  store
    .dispatch("trip/deleteHotel", request)
    .then((res) => {
      deleteDialog.value = false;
      store.dispatch("setSnackbar", {
        message: "Hotel deleted successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error deleting hotel!",
        color: "error",
      });
    });
};
</script>

<style scoped></style>
