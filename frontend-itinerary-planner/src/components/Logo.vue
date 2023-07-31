<template>
  <router-link :to="{ name: 'drawer.home' }" class="text-decoration-none">
    <div :class="`d-inline-flex ${customClass}`">
      <v-icon color="red-accent-3" icon="mdi-plane-car"></v-icon>
      <v-app-bar-title> Itinerary Planner</v-app-bar-title>
    </div>
  </router-link>
</template>

<script setup>
defineProps(["customClass"]);
</script>

<style>
.v-toolbar-title__placeholder {
  color: #ff1744;
}
</style>
