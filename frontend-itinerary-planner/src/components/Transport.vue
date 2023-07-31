<template>
  <v-col cols="12">
    <h5 class="text-center">{{ name }}</h5>
    <v-row justify="center">
      <v-col cols="auto" style="max-width: 90%">
        <v-hover v-slot="{ isHovering, props }">
          <v-sheet class="position-relative" rounded v-bind="props">
            <div
              class="d-flex align-center justify-center position-absolute right-0 bg-grey-lighten-2"
            >
              <v-dialog v-model="editDialog" scrollable>
                <template v-slot:activator="{ props: propsDialog }">
                  <v-btn
                    v-if="isHovering"
                    density="compact"
                    icon="mdi-pencil"
                    size="small"
                    v-bind="propsDialog"
                    variant="text"
                    @click="handleEditTransport(id, placeId)"
                  ></v-btn>
                </template>

                <v-row justify="center">
                  <v-col cols="6">
                    <v-card>
                      <v-card-title>
                        <span class="text-h5">Edit Transport</span>
                      </v-card-title>
                      <v-card-text>
                        <v-form
                          ref="form"
                          v-model="isFormValid"
                          fast-fail
                          @submit.prevent
                        >
                          <v-text-field
                            v-model="editedTransportInit.name"
                            :rules="[(v) => !!v || 'Name is required!']"
                            label="Name"
                            variant="underlined"
                          ></v-text-field>

                          <v-text-field
                            v-model="editedTransportInit.startLocation"
                            :rules="[
                              (v) => !!v || 'Start Location is required!',
                            ]"
                            label="Start Location"
                            variant="underlined"
                          ></v-text-field>

                          <v-text-field
                            v-model="editedTransportInit.endLocation"
                            :rules="[(v) => !!v || 'End Location is required!']"
                            label="End Location"
                            variant="underlined"
                          ></v-text-field>

                          <v-text-field
                            v-model="editedTransportInit.startTime"
                            :rules="[(v) => !!v || 'Start Time is required!']"
                            label="Start Time"
                            type="datetime-local"
                            variant="underlined"
                          ></v-text-field>

                          <v-text-field
                            v-model="editedTransportInit.endTime"
                            :rules="[
                              (v) => !!v || 'End Time is required!',
                              (v) =>
                                v > editedTransportInit.startTime ||
                                'End Time must be greater than Start Time',
                            ]"
                            label="End Time"
                            type="datetime-local"
                            variant="underlined"
                          ></v-text-field>

                          <v-select
                            v-model="editedTransportInit.transportType"
                            :items="['Flight', 'Bus', 'Train', 'Taxi', 'Walk']"
                            label="Transport Type"
                            variant="underlined"
                          ></v-select>

                          <v-text-field
                            v-model="editedTransportInit.cost"
                            label="Cost"
                            prefix="$"
                            type="number"
                            variant="underlined"
                          ></v-text-field>
                        </v-form>
                      </v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          type="submit"
                          @click="handleEditTransportSubmit(placeId)"
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
                        <span class="text-h5">Delete Transport</span>
                      </v-card-title>
                      <v-card-text>
                        Are you sure you want to delete?
                      </v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click="handleDeleteTransportSubmit(id, placeId)"
                        >Delete
                        </v-btn>
                        <v-btn @click="deleteDialog = false"> Close</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-col>
                </v-row>
              </v-dialog>
            </div>

            <div class="d-flex">
              <v-icon
                :icon="`mdi-${transportTypeCalc}`"
                class="align-self-center px-5"
              ></v-icon>

              <div class="flex-column">
                <div class="flex-row">
                  <small>{{ formatTime(startTime) }}</small>
                  <v-icon icon="mdi-circle-small"></v-icon>
                  <span>{{ startLocation }}</span>
                </div>

                <div class="flex-row pa-1">
                  <v-icon icon="mdi-unfold-more-horizontal"></v-icon>
                  <v-badge :content="`\$${cost}`" color="info" inline></v-badge>
                </div>

                <div class="flex-row">
                  <small>{{ formatTime(endTime) }}</small>
                  <v-icon icon="mdi-circle-small"></v-icon>
                  <span>{{ endLocation }}</span>
                </div>
              </div>
            </div>
          </v-sheet>
        </v-hover>
      </v-col>
    </v-row>
    <v-row v-if="index !== arrLength - 1" class="py-2" justify="center">
      <v-icon class="text-center" icon="mdi-chevron-down"></v-icon>
    </v-row>
  </v-col>
</template>

<script setup>
import {computed, reactive, ref} from "vue";
import {useStore} from "vuex";
import {useRoute} from "vue-router";
import {formatTime} from "@/util";

const props = defineProps([
  "placeId",
  "id",
  "name",
  "transportType",
  "startLocation",
  "startTime",
  "endLocation",
  "endTime",
  "cost",
  "index",
  "arrLength",
]);
const store = useStore();
const route = useRoute();
const editDialog = ref(false);
const deleteDialog = ref(false);
const editedTransportInit = reactive({});
const form = ref(null);
const isFormValid = ref(true);
const initialCost = ref(0);

const transportTypeCalc = computed(() => {
  if (props.transportType === "FLIGHT") return "airplane";
  else if (props.transportType === "BUS") return "bus";
  else if (props.transportType === "TRAIN") return "train";
  else if (props.transportType === "TAXI") return "taxi";
  else if (props.transportType === "WALK") return "walk";
});

const handleEditTransport = (editedTransportId, placeId) => {
  const placeIndex = store.getters["trip/getPlaceIndex"](placeId);
  const transportIndex = store.getters["trip/getTransportIndex"](
    placeIndex,
    editedTransportId
  );
  Object.assign(
    editedTransportInit,
    store.state.trip.trip.places[placeIndex].transports[transportIndex]
  );
  initialCost.value = editedTransportInit.cost || 0;
};

const handleEditTransportSubmit = async (placeId) => {
  await form.value.validate();
  if (!isFormValid.value) return;

  const request = {
    tripId: route.params.tripId,
    placeId: placeId,
    transportId: editedTransportInit.id,
    initialCost: initialCost.value,
    ...editedTransportInit,
    transportType: editedTransportInit.transportType.toUpperCase(),
  };
  store
    .dispatch("trip/editTransport", request)
    .then((res) => {
      editDialog.value = false;
      store.dispatch("setSnackbar", {
        message: "Transport edited successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error editing transport!",
        color: "error",
      });
    });
};

const handleDeleteTransportSubmit = (id, placeId) => {
  const request = {
    transportId: id,
    placeId: placeId,
    cost: props.cost,
  };
  store
    .dispatch("trip/deleteTransport", request)
    .then((res) => {
      deleteDialog.value = false;
      store.dispatch("setSnackbar", {
        message: "Transport deleted successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error deleting transport!",
        color: "error",
      });
    });
};
</script>

<style scoped></style>
