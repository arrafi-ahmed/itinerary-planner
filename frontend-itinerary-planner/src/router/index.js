// Composables
import {createRouter, createWebHistory} from "vue-router";
import Register from "@/views/Register";
import Login from "@/views/Login";
import Logout from "@/views/Logout";
import Home from "@/views/Home";
import TripAdd from "@/views/TripAdd";
import TripList from "@/views/TripList";
import TripDetails from "@/views/TripDetails";
import HotelAdd from "@/views/HotelAdd";
import PlaceAdd from "@/views/PlaceAdd";
import EventAdd from "@/views/EventAdd";
import TransportAdd from "@/views/TransportAdd";

const routes = [
  {
    path: "/register",
    name: "drawer.register",
    component: Register,
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: "/login",
    name: "drawer.login",
    component: Login,
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: "/logout",
    name: "simple.logout",
    component: Logout,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/",
    name: "drawer.home",
    component: Home,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/trip/add",
    name: "drawer.tripAdd",
    component: TripAdd,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/trip/list",
    name: "drawer.tripList",
    component: TripList,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/trip/:id",
    name: "drawer.tripDetails",
    component: TripDetails,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/trip/:tripId/add-place",
    name: "drawer.placeAdd",
    component: PlaceAdd,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/trip/:tripId/place/:placeId/add-hotel",
    name: "drawer.hotelAdd",
    component: HotelAdd,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/trip/:tripId/place/:placeId/add-event",
    name: "drawer.eventAdd",
    component: EventAdd,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/trip/:tripId/place/:placeId/add-transport",
    name: "drawer.transportAdd",
    component: TransportAdd,
    meta: {
      requiresAuth: true,
    },
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
