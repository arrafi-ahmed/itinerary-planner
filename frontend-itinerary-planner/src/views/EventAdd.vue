<template>
  <v-container class="fill-height">
    <v-row align="center" class="flex-grow-1" justify="center">
      <v-col sm="4">
        <h3 class="pb-2">Add Event</h3>
        <v-form ref="form" v-model="isFormValid" fast-fail @submit.prevent>
          <v-text-field
            v-model="name"
            :rules="[(v) => !!v || 'Name is required!']"
            label="Name"
            variant="underlined"
          ></v-text-field>

          <v-text-field
            v-model="startTime"
            :rules="[(v) => !!v || 'Start Time is required!']"
            label="Start Time"
            type="datetime-local"
            variant="underlined"
          ></v-text-field>
          <v-text-field
            v-model="endTime"
            :rules="[
              (v) => !!v || 'End Time is required!',
              (v) =>
                v > startTime || 'End Time must be greater than Start Time',
            ]"
            label="End Time"
            type="datetime-local"
            variant="underlined"
          ></v-text-field>

          <v-text-field
            v-model="cost"
            label="Cost"
            prefix="$"
            type="number"
            variant="underlined"
          ></v-text-field>

          <v-text-field
            v-model="note"
            label="Note"
            variant="underlined"
          ></v-text-field>

          <v-btn class="d-block mt-2 mx-auto" type="submit" @click="handleAdd"
          >Add Event
          </v-btn
          >
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import {ref} from "vue";
import {useStore} from "vuex";
import {useRoute, useRouter} from "vue-router";

const store = useStore();
const route = useRoute();
const router = useRouter();
const name = ref("");
const startTime = ref("");
const endTime = ref("");
const cost = ref("");
const note = ref("");
const form = ref(null);
const isFormValid = ref(true);

const handleAdd = async () => {
  await form.value.validate();
  if (!isFormValid.value) return;

  const request = {
    tripId: route.params.tripId,
    placeId: route.params.placeId,
    name: name.value,
    startTime: startTime.value,
    endTime: endTime.value,
    cost: cost.value,
    note: note.value,
  };
  store
    .dispatch("trip/addEvent", request)
    .then((res) => {
      router.push({
        name: "drawer.tripDetails",
        params: {id: request.tripId},
      });
      store.dispatch("setSnackbar", {
        message: "Event added successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error adding event!",
        color: "error",
      });
    });
};
</script>

<style scoped></style>
