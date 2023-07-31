import TripService from "@/service/trip";

export const namespaced = true;

export const state = {
  trips: [],
  trip: {
    id: null,
    name: null,
    startTime: null,
    endTime: null,
    cost: null,
    places: [],
    customer: null,
    expense: 0,
  },
};

export const mutations = {
  addTrip(state, payload) {
    state.trips.push(payload);
  },
  setTrips(state, payload) {
    state.trips = payload;
  },
  setTrip(state, payload) {
    state.trip = payload;
  },
  addPlace(state, payload) {
    state.trip.places.push(payload);
  },
  addHotel(state, payload) {
    state.trip.places.hotel = payload;
  },
  addEvent(state, payload) {
    state.trip.places[payload.placeIndex].events.push(payload.data);
  },
  addTransport(state, payload) {
    state.trip.places[payload.placeIndex].transports.push(payload.data);
  },
  editTrip(state, payload) {
    state.trips[payload.tripIndex] = payload.data;
    console.log(payload.data)
    state.trip = {...state.trip, ...payload.data};
  },
  editPlace(state, payload) {
    console.log(payload);
    state.trip.places[payload.placeIndex] = payload.data;
  },
  editHotel(state, payload) {
    state.trip.places[payload.placeIndex].hotel = payload.data;
  },
  editEvent(state, payload) {
    state.trip.places[payload.placeIndex].events[payload.eventIndex] =
      payload.data;
  },
  editTransport(state, payload) {
    state.trip.places[payload.placeIndex].transports[payload.transportIndex] =
      payload.data;
  },
  deleteTrip(state, payload) {
    state.trips.splice(payload.tripIndex, 1);
    state.trip = {};
  },
  deletePlace(state, payload) {
    state.trip.places.splice(payload.placeIndex, 1);
  },
  deleteHotel(state, payload) {
    state.trip.places[payload.placeIndex].hotel = null;
  },
  deleteEvent(state, payload) {
    state.trip.places[payload.placeIndex].events.splice(payload.eventIndex, 1);
  },
  deleteTransport(state, payload) {
    state.trip.places[payload.placeIndex].transports.splice(
      payload.transportIndex,
      1
    );
  },
  setExpense(state, payload) {
    state.trip.expense = payload;
  },
  addExpense(state, payload) {
    state.trip.expense += payload;
  },
  updateExpense(state, payload) {
    state.trip.expense =
      state.trip.expense + Number(payload.cost) - payload.initialCost;
  },
  removeExpense(state, payload) {
    state.trip.expense -= payload;
  },
};

export const actions = {
  addTrip({commit}, request) {
    return new Promise((resolve, reject) => {
      TripService.createTrip(request)
        .then((response) => {
          commit("addTrip", response.data);
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  getTrips({commit}, request) {
    return new Promise((resolve, reject) => {
      TripService.getTrips(request)
        .then((response) => {
          commit("setTrips", response.data);
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  getTrip({commit}, request) {
    return new Promise((resolve, reject) => {
      TripService.getTrip(request)
        .then((response) => {
          commit("setTrip", response.data);
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  addPlace({commit}, request) {
    return new Promise((resolve, reject) => {
      TripService.addPlace(request)
        .then((response) => {
          commit("addPlace", response.data);
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  addHotel({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.addHotel(request)
        .then((response) => {
          commit("addHotel", response.data);
          if (request.cost) {
            const tripIndex = getters.getTripIndex(request.tripId);
            commit("addExpense", response.data.cost);
          }
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  addEvent({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.addEvent(request)
        .then((response) => {
          const placeIndex = getters.getPlaceIndex(request.placeId);
          commit("addEvent", {data: response.data, placeIndex: placeIndex});
          if (request.cost) {
            const tripIndex = getters.getTripIndex(request.tripId);
            commit("addExpense", response.data.cost);
          }
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  addTransport({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.addTransport(request)
        .then((response) => {
          const placeIndex = getters.getPlaceIndex(request.placeId);
          commit("addTransport", {
            data: response.data,
            placeIndex: placeIndex,
          });
          if (request.cost) {
            const tripIndex = getters.getTripIndex(request.tripId);
            commit("addExpense", response.data.cost);
          }
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  editTrip({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.editTrip(request)
        .then((response) => {
          const tripIndex = getters.getTripIndex(request.tripId);
          commit("editTrip", {
            data: response.data,
            tripIndex: tripIndex,
          });
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  editPlace({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.editPlace(request)
        .then((response) => {
          const placeIndex = getters.getPlaceIndex(request.placeId);
          commit("editPlace", {
            data: response.data,
            placeIndex: placeIndex,
          });
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  editHotel({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.editHotel(request)
        .then((response) => {
          const placeIndex = getters.getPlaceIndex(request.placeId);
          commit("editHotel", {
            data: response.data,
            placeIndex: placeIndex,
          });
          commit("updateExpense", {
            initialCost: request.initialCost,
            cost: request.cost,
          });
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  editEvent({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.editEvent(request)
        .then((response) => {
          const placeIndex = getters.getPlaceIndex(request.placeId);
          const eventIndex = getters.getEventIndex(placeIndex, request.eventId);

          commit("editEvent", {
            data: response.data,
            placeIndex: placeIndex,
            eventIndex: eventIndex,
          });
          commit("updateExpense", {
            initialCost: request.initialCost,
            cost: request.cost,
          });
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  editTransport({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.editTransport(request)
        .then((response) => {
          const placeIndex = getters.getPlaceIndex(request.placeId);
          const transportIndex = getters.getTransportIndex(
            placeIndex,
            request.transportId
          );

          commit("editTransport", {
            data: response.data,
            placeIndex: placeIndex,
            transportIndex: transportIndex,
          });
          commit("updateExpense", {
            initialCost: request.initialCost,
            cost: request.cost,
          });
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  deleteTrip({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.deleteTrip(request)
        .then((response) => {
          const tripIndex = getters.getTripIndex(request.tripId);
          commit("deleteTrip", {
            tripIndex: tripIndex,
          });
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  deletePlace({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.deletePlace(request)
        .then((response) => {
          const placeIndex = getters.getPlaceIndex(request.placeId);
          commit("deletePlace", {
            placeIndex: placeIndex,
          });
          commit("removeExpense", request.cost);
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  deleteHotel({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.deleteHotel(request)
        .then((response) => {
          const placeIndex = getters.getPlaceIndex(request.placeId);

          commit("deleteHotel", {
            placeIndex: placeIndex,
          });
          commit("removeExpense", request.cost);
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  deleteEvent({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.deleteEvent(request)
        .then((response) => {
          const placeIndex = getters.getPlaceIndex(request.placeId);
          const eventIndex = getters.getEventIndex(placeIndex, request.eventId);

          commit("deleteEvent", {
            data: response.data,
            placeIndex: placeIndex,
            eventIndex: eventIndex,
          });
          commit("removeExpense", request.cost);
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  deleteTransport({commit, getters}, request) {
    return new Promise((resolve, reject) => {
      TripService.deleteTransport(request)
        .then((response) => {
          const placeIndex = getters.getPlaceIndex(request.placeId);
          const transportIndex = getters.getTransportIndex(
            placeIndex,
            request.transportId
          );

          commit("deleteTransport", {
            data: response.data,
            placeIndex: placeIndex,
            transportIndex: transportIndex,
          });
          commit("removeExpense", request.cost);
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
};

export const getters = {
  getTripIndex: (state) => (tripId) => {
    return state.trips.findIndex((trip) => trip.id == tripId);
  },
  getPlaceIndex: (state) => (placeId) => {
    return state.trip.places.findIndex((place) => place.id == placeId);
  },
  getEventIndex: (state) => (placeIndex, eventId) => {
    return state.trip.places[placeIndex].events.findIndex(
      (event) => event.id == eventId
    );
  },
  getTransportIndex: (state) => (placeIndex, transportId) => {
    return state.trip.places[placeIndex].transports.findIndex(
      (transport) => transport.id == transportId
    );
  },
};
