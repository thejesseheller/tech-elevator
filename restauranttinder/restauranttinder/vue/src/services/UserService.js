import axios from 'axios';

const apiKey = '41f5d45df4f69640bf400a9aba7571c5';

const http = axios.create({
  baseURL: "https://developers.zomato.com/api/v2.1",
  headers: {
    'user-key': apiKey,
    'content-type': "application/json"
  }
});

export default {
  getRestByCuisineLatLng(cuisineId, lat, lng) {
    return http.get(`/search?count=20&lat=${lat}&lon=${lng}&cuisines=${cuisineId}&apikey=${apiKey}`)
  },

  getAllRestByZip(lat, lng, offset) {
    return http.get(`/search?start=${offset}&count=100&lat=${lat}&lon=${lng}&apikey=${apiKey}`)    
  },

  getRestaurantsByCustomSearch(lat, lng, searchTerm) {
    return http.get(`search?q=${searchTerm}&lat=${lat}&lon=${lng}&apikey=${apiKey}`)
  },

  getRestById(restId) {
    return http.get(`/restaurant?count=20&res_id=${restId}&apikey=${apiKey}`)
  }
    
}

/* https://developers.zomato.com/api/v2.1/
search?lat=41.991504&lon=-87.703795&cuisines=25 */