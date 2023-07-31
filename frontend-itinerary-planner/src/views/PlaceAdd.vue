<template>
  <v-container class="fill-height">
    <v-row align="center" class="flex-grow-1" justify="center">
      <v-col sm="4">
        <h3 class="pb-2">Add Place</h3>
        <v-form ref="form" v-model="isFormValid" fast-fail @submit.prevent>
          <v-text-field
            v-model="name"
            :rules="[(v) => !!v || 'Name is required!']"
            label="Name"
            variant="underlined"
          ></v-text-field>
          <v-text-field
            v-model="address"
            :rules="[(v) => !!v || 'Address is required']"
            label="Address"
            required
            variant="underlined"
          ></v-text-field>
          <v-btn class="d-block mt-2 mx-auto" type="submit" @click="handleAdd"
          >Add Place
          </v-btn>
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
const address = ref("");
const form = ref(null);
const isFormValid = ref(true);

const handleAdd = async () => {
  await form.value.validate();
  if (!isFormValid.value) return;

  const request = {
    tripId: route.params.tripId,
    name: name.value,
    address: address.value,
  };

  store
    .dispatch("trip/addPlace", request)
    .then((res) => {
      router.push({
        name: "drawer.tripDetails",
        params: {id: request.tripId},
      });
      store.dispatch("setSnackbar", {
        message: "Place added successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error adding place!",
        color: "error",
      });
    });
};
</script>

<style scoped></style>
