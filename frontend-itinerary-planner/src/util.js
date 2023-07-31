import axios from "axios";
import store from "@/store";

export const setupAxiosInterceptor = () => {
  axios.interceptors.request.use(
    (config) => {
      const token = store.getters["customer/getToken"];
      const loggedIn = store.getters["customer/loggedIn"];
      if (loggedIn) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (err) => {
      return Promise.reject(err);
    }
  );
};

export const formatTime = (time) => {
  const dateTime = new Date(time);
  const month = dateTime.toLocaleString("default", {month: "short"});
  const date = dateTime.getDate();
  const hours = dateTime.getHours();
  const mins = dateTime.getMinutes();
  return month + " " + date + ", " + hours + ":" + mins;
};

export const formatDate = (time) => {
  const dateTime = new Date(time);
  const month = dateTime.toLocaleString("default", {month: "short"});
  const date = dateTime.getDate();
  return month + " " + date;
};

export const calculateExpense = (place) => {
  console.log(place);
  let expense = 0;
  expense += place.hotel && place.hotel.cost ? place.hotel.cost : 0;
  if (place.events.length > 0) {
    place.events.forEach((event) => {
      expense += event.cost ? event.cost : 0;
    });
  }
  if (place.transports.length > 0) {
    place.transports.forEach((transport) => {
      expense += transport.cost ? transport.cost : 0;
    });
  }
  return expense;
};
