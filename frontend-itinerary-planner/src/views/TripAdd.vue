<template>
  <v-container class="fill-height">
    <v-row align="center" class="flex-grow-1" justify="center">
      <v-col sm="4">
        <h3 class="pb-2">Add Trip</h3>
        <v-form
          ref="form"
          v-model="isFormValid"
          fast-fail
          @submit.prevent="handleAdd"
        >
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
            type="date"
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
            type="date"
            variant="underlined"
          ></v-text-field>

          <v-text-field
            v-model="cost"
            :rules="[(v) => !!v || 'Budget is required']"
            label="Budget"
            prefix="$"
            required
            type="number"
            variant="underlined"
          ></v-text-field>

          <v-btn class="d-block mt-2 mx-auto" type="submit">Add Trip</v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import {ref} from "vue";
import {useStore} from "vuex";
import router from "@/router";

const store = useStore();
const name = ref("");
const startTime = ref("");
const endTime = ref("");
const cost = ref("");
const form = ref(null);
const isFormValid = ref(true);

const handleAdd = async () => {
  await form.value.validate();
  if (!isFormValid.value) return;

  const request = {
    name: name.value,
    startTime: startTime.value,
    endTime: endTime.value,
    cost: cost.value,
  };
  store
    .dispatch("trip/addTrip", request)
    .then((res) => {
      router.push({name: "drawer.tripList"});
      store.dispatch("setSnackbar", {
        message: "Trip added successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      console.dir(err);
      store.dispatch("setSnackbar", {
        message: "Error adding trip!",
        color: "error",
      });
    });
};
</script>

<style scoped></style>
