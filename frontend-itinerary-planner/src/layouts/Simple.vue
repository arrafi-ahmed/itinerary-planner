<template>
  <v-app>
    <v-app-bar flat>
      <logo customClass="px-6"/>
    </v-app-bar>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>
<script setup>
import Logo from "@/components/Logo";</script>
