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
                @click="handleEditEvent(id, placeId)"
              ></v-btn>
            </template>

            <v-row justify="center">
              <v-col cols="6">
                <v-card>
                  <v-card-title>
                    <span class="text-h5">Edit Event</span>
                  </v-card-title>
                  <v-card-text>
                    <v-form
                      ref="form"
                      v-model="isFormValid"
                      fast-fail
                      @submit.prevent
                    >
                      <v-text-field
                        v-model="editedEventInit.name"
                        :rules="[(v) => !!v || 'Name is required!']"
                        label="Name"
                        variant="underlined"
                      ></v-text-field>

                      <v-text-field
                        v-model="editedEventInit.startTime"
                        :rules="[(v) => !!v || 'Start Time is required!']"
                        label="Start Time"
                        type="datetime-local"
                        variant="underlined"
                      ></v-text-field>

                      <v-text-field
                        v-model="editedEventInit.endTime"
                        :rules="[
                          (v) => !!v || 'End Time is required!',
                          (v) =>
                            v > editedEventInit.startTime ||
                            'End Time must be greater than Start Time',
                        ]"
                        label="End Time"
                        type="datetime-local"
                        variant="underlined"
                      ></v-text-field>

                      <v-text-field
                        v-model="editedEventInit.cost"
                        label="Cost"
                        prefix="$"
                        type="number"
                        variant="underlined"
                      ></v-text-field>

                      <v-text-field
                        v-model="editedEventInit.note"
                        label="Note"
                        variant="underlined"
                      ></v-text-field>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn type="submit" @click="handleEditEventSubmit(placeId)"
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
                    <span class="text-h5">Delete Event</span>
                  </v-card-title>
                  <v-card-text> Are you sure you want to delete?</v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn @click="handleDeleteEventSubmit(id, placeId)"
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
          color="green"
          icon="mdi-calendar"
          size="25"
        ></v-icon>
        <div class="flex-row pa-2">
          <v-badge :content="`\$${cost}`" color="info" inline></v-badge>
          <h3 class="d-inline-block">{{ name }}</h3>
          <v-icon>mdi-circle-small</v-icon>
          <strong>Start:</strong> {{ formatTime(startTime) }}
          <v-icon> mdi-circle-small</v-icon>
          <strong>End:</strong> {{ formatTime(endTime) }}
          <v-icon v-if="note"> mdi-circle-small</v-icon>
          {{ note }}
        </div>
      </v-sheet>
    </v-hover>
  </v-col>
</template>

<script setup>
import {useStore} from "vuex";
import {formatTime} from "@/util";
import {useRoute} from "vue-router";
import {reactive, ref} from "vue";

const props = defineProps([
  "placeId",
  "id",
  "name",
  "startTime",
  "endTime",
  "cost",
  "note",
]);
const store = useStore();
const route = useRoute();
const editDialog = ref(false);
const deleteDialog = ref(false);
const editedEventInit = reactive({});
const form = ref(null);
const isFormValid = ref(true);
const initialCost = ref(0);

const handleEditEvent = (editedEventId, placeId) => {
  const placeIndex = store.getters["trip/getPlaceIndex"](placeId);
  const eventIndex = store.getters["trip/getEventIndex"](
    placeIndex,
    editedEventId
  );
  Object.assign(
    editedEventInit,
    store.state.trip.trip.places[placeIndex].events[eventIndex]
  );
  initialCost.value = editedEventInit.cost || 0;
};

const handleEditEventSubmit = async (placeId) => {
  await form.value.validate();
  if (!isFormValid.value) return;

  const request = {
    tripId: route.params.tripId,
    placeId: placeId,
    eventId: editedEventInit.id,
    initialCost: initialCost.value,
    ...editedEventInit,
  };
  store
    .dispatch("trip/editEvent", request)
    .then((res) => {
      editDialog.value = false;
      store.dispatch("setSnackbar", {
        message: "Event edited successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error editing event!",
        color: "error",
      });
    });
};

const handleDeleteEventSubmit = (id, placeId) => {
  const request = {
    eventId: id,
    placeId: placeId,
    cost: props.cost,
  };
  store
    .dispatch("trip/deleteEvent", request)
    .then((res) => {
      deleteDialog.value = false;
      store.dispatch("setSnackbar", {
        message: "Event deleted successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error deleting event!",
        color: "error",
      });
    });
};
</script>

<style scoped></style>
