import axios from "axios";
import store from "../store";

const baseUrl = import.meta.env.VITE_API_BASE_URL;

const TripService = {
  createTrip(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${baseUrl}/customer/${store.getters["customer/getId"]}/addTrip`,
          request
        )
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  getTrips(request) {
    return new Promise((resolve, reject) => {
      axios
        .get(
          `${baseUrl}/trip/customer-${store.getters["customer/getId"]}`,
          request
        )
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  getTrip(tripId) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseUrl}/trip/${tripId}`)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  addPlace(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/trip/${request.tripId}/addPlace`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  addHotel(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/place/${request.placeId}/addHotel`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  addEvent(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/place/${request.placeId}/addEvent`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  addTransport(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/place/${request.placeId}/addTransport`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  editTrip(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/trip/${request.tripId}/update`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  editPlace(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/place/${request.placeId}/update`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  editHotel(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/hotel/${request.hotelId}/update`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  editEvent(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/event/${request.eventId}/update`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  editTransport(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/transport/${request.transportId}/update`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  deleteTrip(request) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseUrl}/trip/${request.tripId}/delete`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  deletePlace(request) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseUrl}/place/${request.placeId}/delete`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  deleteHotel(request) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseUrl}/hotel/${request.hotelId}/delete`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  deleteEvent(request) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseUrl}/event/${request.eventId}/delete`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  deleteTransport(request) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseUrl}/transport/${request.transportId}/delete`, request)
        .then((response) => {
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
};

export default TripService;
