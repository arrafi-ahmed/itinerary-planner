<template>
  <v-container id="printable-content">
    <v-row justify="center">
      <v-col cols="12">
        <v-hover v-slot="{ isHovering, props }">
          <v-row
            align="center"
            class="position-relative"
            justify="space-between"
            v-bind="props"
          >
            <v-col cols="auto">
              <h2 class="my-5 d-inline-block">{{ trip.name }}</h2>
              <div>
                <span>
                  Budget:
                  <v-badge
                    :content="`\$${trip.cost}`"
                    color="success"
                    inline
                  ></v-badge>
                </span>
                <span class="px-1"> | </span>
                <span>
                  Expense:
                  <v-badge
                    :content="`\$${tripExpense}`"
                    color="error"
                    inline
                  ></v-badge>
                </span>
              </div>
            </v-col>
            <v-col class="d-print-none" cols="auto">
              <v-row>
                <v-btn
                  color="deep-purple-darken-1"
                  density="comfortable"
                  prepend-icon="mdi-printer"
                  variant="outlined"
                  @click="print"
                >
                  Print
                </v-btn>
              </v-row>
              <v-row>
                <v-btn
                  :to="{
                    name: 'drawer.placeAdd',
                    params: { tripId: `${trip.id}` },
                  }"
                  class="mt-2"
                  color="deep-purple-darken-1"
                  density="comfortable"
                  prepend-icon="mdi-plus"
                  variant="outlined"
                >
                  Place
                </v-btn>
              </v-row>
            </v-col>

            <div
              class="position-absolute right-120 bg-grey-lighten-2 d-print-none"
            >
              <v-dialog v-model="editDialogTrip" scrollable>
                <template v-slot:activator="{ props: propsDialog }">
                  <v-btn
                    v-if="isHovering"
                    density="compact"
                    icon="mdi-pencil"
                    size="small"
                    v-bind="propsDialog"
                    variant="text"
                    @click="handleEditTrip"
                  ></v-btn>
                </template>

                <v-row justify="center">
                  <v-col cols="6">
                    <v-card>
                      <v-card-title>
                        <span class="text-h5">Edit Trip</span>
                      </v-card-title>
                      <v-card-text>
                        <v-form
                          ref="editTripForm"
                          v-model="isEditTripFormValid"
                          fast-fail
                          @submit.prevent
                        >
                          <v-text-field
                            v-model="editedTripInit.name"
                            :rules="[(v) => !!v || 'Name is required!']"
                            label="Name"
                            variant="underlined"
                          ></v-text-field>

                          <v-text-field
                            v-model="editedTripInit.startTime"
                            :rules="[(v) => !!v || 'Start Time is required!']"
                            label="Start Time"
                            type="date"
                            variant="underlined"
                          ></v-text-field>

                          <v-text-field
                            v-model="editedTripInit.endTime"
                            :rules="[
                              (v) => !!v || 'End Time is required!',
                              (v) =>
                                v > editedTripInit.startTime ||
                                'End Time must be greater than Start Time',
                            ]"
                            label="End Time"
                            type="date"
                            variant="underlined"
                          ></v-text-field>

                          <v-text-field
                            v-model="editedTripInit.cost"
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
                          @click="handleEditTripSubmit(trip.id)"
                        >Edit
                        </v-btn>
                        <v-btn @click="editDialogTrip = false"> Close</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-col>
                </v-row>
              </v-dialog>

              <v-dialog v-model="deleteDialogTrip" scrollable>
                <template v-slot:activator="{ props: propsDialog }">
                  <v-btn
                    v-if="isHovering"
                    color="red-lighten-1"
                    density="compact"
                    icon="mdi-close"
                    size="small"
                    v-bind="propsDialog"
                    variant="text"
                    @click="deleteDialogTrip = true"
                  ></v-btn>
                </template>

                <v-row justify="center">
                  <v-col cols="6">
                    <v-card>
                      <v-card-title>
                        <span class="text-h5">Delete Trip</span>
                      </v-card-title>
                      <v-card-text>
                        Are you sure you want to delete?
                      </v-card-text
                      >
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click="handleDeleteTripSubmit(trip.id)"
                        >Delete
                        </v-btn>
                        <v-btn @click="deleteDialogTrip = false"> Close</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-col>
                </v-row>
              </v-dialog>
            </div>
          </v-row>
        </v-hover>
        <!--      place-->
        <v-row
          v-for="(place, index) in trip.places"
          v-if="trip.places?.length > 0"
        >
          <v-col cols="12">
            <v-row>
              <v-col>
                <v-card border rounded>
                  <v-hover v-slot="{ isHovering, props }" class="d-print-none">
                    <v-row justify="space-between" v-bind="props">
                      <v-col cols="auto">
                        <div
                          class="position-absolute right-40 bg-grey-lighten-2"
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
                                @click="handleEditPlace(place.id)"
                              ></v-btn>
                            </template>

                            <v-row justify="center">
                              <v-col cols="6">
                                <v-card>
                                  <v-card-title>
                                    <span class="text-h5">Edit Place</span>
                                  </v-card-title>
                                  <v-card-text>
                                    <v-form
                                      ref="editPlaceForm"
                                      v-model="isEditPlaceFormValid"
                                      fast-fail
                                      @submit.prevent
                                    >
                                      <v-text-field
                                        v-model="editedPlaceInit.name"
                                        :rules="[
                                          (v) => !!v || 'Name is required!',
                                        ]"
                                        label="Name"
                                        variant="underlined"
                                      ></v-text-field>

                                      <v-text-field
                                        v-model="editedPlaceInit.address"
                                        :rules="[
                                          (v) => !!v || 'Address is required',
                                        ]"
                                        label="Address"
                                        variant="underlined"
                                      ></v-text-field>
                                    </v-form>
                                  </v-card-text>
                                  <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn
                                      type="submit"
                                      @click="
                                        handleEditPlaceSubmit(
                                          editedPlaceInit.id
                                        )
                                      "
                                    >Edit
                                    </v-btn>
                                    <v-btn @click="editDialog = false">
                                      Close
                                    </v-btn
                                    >
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
                                @click="handleDeletePlace(place.id)"
                              ></v-btn>
                            </template>

                            <v-row justify="center">
                              <v-col cols="6">
                                <v-card>
                                  <v-card-title>
                                    <span class="text-h5">Delete Place</span>
                                  </v-card-title>
                                  <v-card-text>
                                    Are you sure you want to delete?
                                  </v-card-text>
                                  <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn @click="handleDeletePlaceSubmit"
                                    >Delete
                                    </v-btn>
                                    <v-btn @click="deleteDialog = false">
                                      Close
                                    </v-btn
                                    >
                                  </v-card-actions>
                                </v-card>
                              </v-col>
                            </v-row>
                          </v-dialog>
                        </div>

                        <div class="d-inline-flex align-center">
                          <v-card-title>{{ place.name }}</v-card-title>
                          <v-card-subtitle>{{ place.address }}</v-card-subtitle>
                        </div>
                      </v-col>

                      <v-col class="d-print-none" cols="auto">
                        <v-menu>
                          <template v-slot:activator="{ props }">
                            <v-btn
                              class="ma-2"
                              flat
                              icon="mdi-plus"
                              size="sm"
                              v-bind="props"
                              variant="outlined"
                            ></v-btn>
                          </template>

                          <v-list>
                            <v-list-item
                              :to="{
                                name: 'drawer.hotelAdd',
                                params: { tripId: trip.id, placeId: place.id },
                              }"
                            >
                              <v-list-item-title>Hotel</v-list-item-title>
                            </v-list-item>
                            <v-list-item
                              :to="{
                                name: 'drawer.eventAdd',
                                params: { tripId: trip.id, placeId: place.id },
                              }"
                            >
                              <v-list-item-title>Event</v-list-item-title>
                            </v-list-item>
                            <v-list-item
                              :to="{
                                name: 'drawer.transportAdd',
                                params: { tripId: trip.id, placeId: place.id },
                              }"
                            >
                              <v-list-item-title>Transport</v-list-item-title>
                            </v-list-item>
                          </v-list>
                        </v-menu>
                      </v-col>
                    </v-row>
                  </v-hover>
                  <v-card-text>
                    <v-row v-if="place.hotel">
                      <hotel
                        :id="place.hotel.id"
                        :address="place.hotel.address"
                        :cost="place.hotel.cost"
                        :end-time="place.hotel.endTime"
                        :name="place.hotel.name"
                        :place-id="place.id"
                        :start-time="place.hotel.startTime"
                      ></hotel>
                    </v-row>
                    <v-row v-if="place.events.length > 0">
                      <event
                        v-for="(event, index) in place.events"
                        :id="event.id"
                        :cost="event.cost"
                        :end-time="event.endTime"
                        :name="event.name"
                        :note="event.note"
                        :place-id="place.id"
                        :start-time="event.startTime"
                      ></event>
                    </v-row>
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
            <v-row v-if="place.transports.length > 0">
              <transport
                v-for="(transport, index) in place.transports"
                :id="transport.id"
                :arrLength="place.transports.length"
                :cost="transport.cost"
                :end-location="transport.endLocation"
                :end-time="transport.endTime"
                :index="index"
                :name="transport.name"
                :place-id="place.id"
                :start-location="transport.startLocation"
                :start-time="transport.startTime"
                :transport-type="transport.transportType"
              />
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import Transport from "@/components/Transport";
import Hotel from "@/components/Hotel";
import Event from "@/components/Event";
import {computed, onMounted, reactive, ref} from "vue";
import {useStore} from "vuex";
import {useRoute, useRouter} from "vue-router";
import {calculateExpense} from "@/util";

const route = useRoute();
const router = useRouter();
const store = useStore();
const trips = computed(() => store.state.trip.trips);
const trip = computed(() => store.state.trip.trip);
const tripExpense = computed(() => store.state.trip.trip.expense);
const editDialog = ref(false);
const deleteDialog = ref(false);
const editDialogTrip = ref(false);
const deleteDialogTrip = ref(false);
const editedPlaceInit = reactive({});
const editedTripInit = reactive({});
const editTripForm = ref(null);
const isEditTripFormValid = ref(true);
const editPlaceForm = ref(null);
const isEditPlaceFormValid = ref(true);
const deletingPlaceId = ref(null);

const handleEditTrip = () => {
  Object.assign(editedTripInit, store.state.trip.trip);
};

const handleEditTripSubmit = async (tripId) => {
  await editTripForm.value.validate();
  if (!isEditTripFormValid.value) return;

  const request = {
    tripId: tripId,
    ...editedTripInit,
  };
  delete request.places;

  store
    .dispatch("trip/editTrip", request)
    .then((res) => {
      editDialogTrip.value = false;
      store.dispatch("setSnackbar", {
        message: "Trip edited successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error editing trip!",
        color: "error",
      });
    });
};

const handleDeleteTripSubmit = (tripId) => {
  const request = {
    tripId: tripId,
  };
  store
    .dispatch("trip/deleteTrip", request)
    .then((res) => {
      router.push({name: "drawer.tripList"});
      store.dispatch("setSnackbar", {
        message: "Trip deleted successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error deleting trip!",
        color: "error",
      });
    });
};

const handleEditPlace = (placeId) => {
  const placeIndex = store.getters["trip/getPlaceIndex"](placeId);
  Object.assign(editedPlaceInit, store.state.trip.trip.places[placeIndex]);
};

const handleEditPlaceSubmit = (placeId) => {
  // await editPlaceForm.validate();
  // if (!isEditPlaceFormValid.value) return;
  const request = {
    tripId: route.params.tripId,
    placeId: placeId,
    ...editedPlaceInit,
  };
  delete request.events;
  delete request.hotel;
  delete request.transports;

  store
    .dispatch("trip/editPlace", request)
    .then((res) => {
      editDialog.value = false;
      store.dispatch("setSnackbar", {
        message: "Place edited successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error editing place!",
        color: "error",
      });
    });
};
const handleDeletePlace = (placeId) => {
  console.log(placeId);
  deleteDialog.value = true;
  deletingPlaceId.value = placeId;
};
const handleDeletePlaceSubmit = () => {
  const placeIndex = store.getters["trip/getPlaceIndex"](deletingPlaceId.value);
  const place = store.state.trip.trip.places[placeIndex];
  let expense = calculateExpense(place);
  const request = {
    placeId: deletingPlaceId.value,
    cost: expense,
  };
  store
    .dispatch("trip/deletePlace", request)
    .then((res) => {
      deleteDialog.value = false;
      store.dispatch("setSnackbar", {
        message: "Place deleted successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error deleting place!",
        color: "error",
      });
    });
};

const print = () => {
  const printableContent =
    document.getElementById("printable-content").parentNode;
  const printWindow = window.open("", "", "height=auto,width=800");
  printWindow.document.write(
    `<html><head><title>Itinerary Planner</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/vuetify@3.0.0/dist/vuetify.min.css">
    <link rel="stylesheet" href="https://cdn.materialdesignicons.com/5.4.55/css/materialdesignicons.min.css">
    </head><body>
    Generated by Itinerary Planner V0.1
    ${printableContent.innerHTML}
    </body></html>`
  );
  printWindow.document.close();
  setTimeout(() => {
    printWindow.print();
    printWindow.close();
  }, 1000);
};

onMounted(() => {
  store
    .dispatch("trip/getTrip", route.params.id)
    .then((res) => {
      let expense = 0;
      if (res.data.places.length > 0) {
        res.data.places.forEach((place) => {
          expense += calculateExpense(place);
        });
      }
      store.commit("trip/setExpense", expense);
    })
    .catch((err) => {
      console.log(err);
    });
});
</script>

<style scoped></style>
