<template>
  <v-container class="fill-height">
    <v-row align="center" class="flex-grow-1" justify="center">
      <v-col sm="4">
        <h3 class="pb-2">Trip List</h3>
        <v-list v-if="trips.length > 0" lines="two">
          <v-hover v-slot="{ isHovering, props }">
            <v-list-item
              v-for="(trip, index) in trips"
              :key="index"
              :subtitle="trip.startTime"
              :title="trip.name"
              :to="{ name: 'drawer.tripDetails', params: { id: trip.id } }"
            >
              <template v-slot:prepend>
                <v-avatar color="green">
                  <v-icon color="white">mdi-wallet-travel</v-icon>
                </v-avatar>
              </template>
            </v-list-item>
          </v-hover>
        </v-list>
        <h5 v-else>No trips listed yet!</h5>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import {computed, onMounted} from "vue";
import {useStore} from "vuex";

const store = useStore();
const trips = computed(() => store.state.trip.trips);

onMounted(() => {
  store
    .dispatch("trip/getTrips")
    .then((res) => {
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error loading trips!",
        color: "error",
      });
    });
});
</script>

<style scoped></style>
