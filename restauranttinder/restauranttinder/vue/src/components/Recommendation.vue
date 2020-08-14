<template>
  <div id="main" class="blur">
    <div class="rest-card">      
      <h2 class="rest-name">{{randomRestaurant.name}}</h2>
      <a v-bind:href="randomRestaurant.url" target="_blank"><img v-bind:src="displayImage"/></a>
      <p id="rest-cuisine">Cuisine Type: {{randomRestaurant.cuisines}}</p>
      <span>
      <p>
        <span class="dollar-sign" v-if="randomRestaurant.price_range === 1">$</span>
        <span class="dollar-sign" v-else-if="randomRestaurant.price_range === 2">$$</span>
        <span class="dollar-sign" v-else-if="randomRestaurant.price_range === 3">$$$</span>
        <span class="dollar-sign" v-else-if="randomRestaurant.price_range === 4">$$$$</span>
        <span class="dollar-sign" v-else>$$$$$</span>
      </p>
      </span>
      <p id="rest-ratings">Star Rating: {{randomRestaurant.user_rating.aggregate_rating}} / 5</p>
      <p id="rest-description">{{highlightItems}}</p>
      </div>
      <div class="like-reject">
        <span>
        <a><i id="like-btn" @click="likeRestaurant" class="fa fa-heart"></i></a>
        </span>
        <span>
          <a id="recommendation-phone" href= "tel:parsePhone"><i class="fa fa-phone"></i></a>
        </span>
        <span>
          <a id="recommendation-menu" v-bind:href="randomRestaurant.menu_url" target="_blank"><i class ="fa fa-book"></i></a>
        </span>
        <span>
          <a id="recommendation-car"><i  class="fa fa-car" @click="openGoogleMap(randomRestaurant)"></i></a>
        </span>
        <span>
        <a><i id="reject-btn" @click="rejectRestaurant" class="fa fa-thumbs-down"></i></a>
        </span>
      </div>
      <div class="search-box">
      <search-box />
        <div class="search-anything">
        <span class="search-anything">
      <input type="text" id="searchTerm" v-model="searchTerm" placeholder="Search any cuisine"/>
      <label for="searchTerm" class="sr-only"></label>
      <button @click="searchByKeyword" id="submitbtn" class="submit-btn">Submit</button>
        </span>
        </div>
      </div>
    <div class="btn-container">
     <div class="cuisine-checkbox" v-for="cuisine in cuisines" :key="cuisine.cuisineId">
      <button id="btn" class="cuisine-button" v-on:click="setCuisineId(cuisine.cuisineId)">{{cuisine.cuisineName}}</button>
     </div>
    </div>   
    <!-- <favorites-button class="fav-btn"/> -->
  </div>
</template>

<script>
import userService from '../services/UserService';
import DBService from '../services/DBService';
import searchBox from '../components/SearchBox';

export default {
  name: "recommendation",
  components: {
    searchBox
  },

  data () {
    return {
      searchTerm: '',
      highlightItems: '',
      phoneNumberStorage: '',
      randomCuisineId: '',
      cuisines: [{
        cuisine: {
          cuisineId: '',
          cuisineName: ''
        }
      }],
      displayImage: '',
      randomRestaurant: {},
      restaurants: [{
        restaurant: {}
      }]
     }
    },

  computed: {
    randomIndex() {
      return Math.floor((Math.random() * this.restaurants.length));
    },
    offset() {
        return Math.floor((Math.random() * 75) + 1);
      },
    dollarprice: function() {
      let dollarsigns = "";

      if (this.restaurants) {
        for (let i = 0; i < this.restaurants[i].price_range; i++) {
          dollarsigns += "$";
        }
      }
      return dollarsigns;
    },
   
  },
  watch: {
    randomIndex() {        
      this.showSingleLocation();
      this.highlightItems = '';
      this.parseHighlights();
    },

    '$store.state.user.lat': function() {
      this.callRest();
    }
  }, 

  methods: {
    parseHighlights() {
      for (let i = 0; i < this.randomRestaurant.highlights.length; i++) {
        let str = this.randomRestaurant.highlights[i];
        str = str.replace(/("|')/g, "");
        if (i < this.randomRestaurant.highlights.length - 1) {
          this.highlightItems += str + ', ';
        } else {
          this.highlightItems += str;
        }
      }
    },

    parsePhone() {
      let helper = 1 + "-" + this.randomRestaurant.phone_numbers.replace("(", " ").replace(")", "-");
      this.phoneNumberStorage = helper;
    },

    openGoogleMap(item) {
      const urlSuffix = item.lat
        ? item.lng
        : item.location.address.replace(" ", "+") + ", " +
        item.location.locality.replace(" ", "+") + ", " +
        item.location.city.replace(" ", "+") + ", " +
        item.location.zipcode + ", " +
        item.name;

        window.open("https://www.google.com/maps/search/?api=1&query=" + urlSuffix,
        "_blank");
    },

    setFeaturedImage() {
      if (this.randomRestaurant.featured_image === '') {
        this.randomRestaurant.featured_image = "https://i.imgur.com/9QA0xs7.png";
        return this.displayImage = "https://i.imgur.com/9QA0xs7.png";
      } else {
        return this.displayImage = this.randomRestaurant.featured_image;
      }

    },

    searchByKeyword() {
      if (this.searchTerm !== '') {
      userService.getRestaurantsByCustomSearch(this.$store.state.user.lat, this.$store.state.user.lng, this.searchTerm).then(response => {
        this.restaurants = response.data.restaurants;
        this.searchTerm = '';
      }).catch (error => {
              if (error.response && error.response.status === 404) {
                alert("Restaurants not available");
            }
          });
        }
    },

    getRestByCuisineLatLng() {             
      userService.getRestByCuisineLatLng(this.randomCuisineId, this.$store.state.user.lat, this.$store.state.user.lng).then(response => {
        this.restaurants = response.data.restaurants;
          }).catch (error => {
              if (error.response && error.response.status === 404) {
                alert("Restaurants not available");
          }
        }
      );
    },
    setCuisineId(cuisineId) {
      this.randomCuisineId = cuisineId;
      this.getRestByCuisineLatLng();
    },

    likeRestaurant() {          
      if(this.restaurants.length < 2) {
        this.callRest();
        alert("Loading more restaurants you hungry bastard.");  
    }            
      DBService.saveFavorite(this.$store.state.user.id, this.randomRestaurant)
        .catch((err) => {console.error(err + ' Problem saving favorite');
              alert("Restaurant has already been favorited");          
          }
        )
        this.restaurants.splice(this.randomIndex, 1);          
    },

    rejectRestaurant() {                    
      if (this.restaurants.length < 2) {
        alert("Loading more restaurants you hungry bastard.");
          this.callRest();
        
      } else {
      this.restaurants.splice(this.randomIndex, 1);
      }
      },          

    showSingleLocation() {           
      this.randomRestaurant = this.restaurants[this.randomIndex].restaurant;
      this.displayImage = this.setFeaturedImage();         
    },

    callRest() {
    userService.getAllRestByZip(this.$store.state.user.lat, this.$store.state.user.lng, this.offset).then(response => {
      this.restaurants = response.data.restaurants;                      
      this.randomRestaurant = this.restaurants[this.randomIndex].restaurant;
      this.displayImage = this.randomRestaurant.featured_image;
    }).catch (error => {
        if (error.response && error.response.status === 404) {
          alert("Restaurants not available");
        }
    });
  }
},
    created() {
    DBService.getCuisines().then(response => {
      this.cuisines = response.data;
    });
      this.callRest();
    }
}

</script>

<style>
#main {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  min-width: 100vw;

}
.rest-card {
  border-radius: 20px;
  width: 75%;
  height: auto;
  margin: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: rgba(26, 25, 25, 0.418);
  color: rgb(255, 255, 255);
  font-family: 'Cookie', cursive;
  max-width: 600px;
  padding: 5px;
  

}
.rest-card h2 {
  font-family:'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
  font-weight: bold;
}
.rest-card img {
  border-radius: 20px;
}
.rest-card p {
  border: 2px solid rgba(218, 20, 20, 0.602);
  border-radius: 10px;
  background-color:rgba(218, 20, 20, 0.602);
  font-weight: bold;
  padding: 5px;
  margin: 5px;
  font-family:'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
}
#rest-description {
  max-width: 80%;
  margin-bottom: .75rem;
}
.rest-name {
  padding: 8px;
}
.rest-card img {
  margin-bottom: 10px;
}
.like-reject {
  display: flex;
  flex-direction: row;
  margin: 5px;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 3%;
  max-width: 650px;

}
.like-reject a:hover {
   -webkit-transform: scale(1.2);
  -ms-transform: scale(1.2);
  transform: scale(1.2);
  text-shadow: -1px 1px 15px rgb(248, 56, 8),
	1px 1px 15px #000,
	1px 0px 0 #000,
	-1px -1px 0 #000;

}
.search-box {
  display: flex;
  flex-direction: row;
  justify-content: left;
  align-items: center;
}
.search-anything{
  display: flex;
  flex-direction: row;
  margin-top: .25rem;
  margin-bottom: .5rem;
  width: auto;
  align-content: center;
}
.search-anything input {
    padding: 5px;
    border-radius: 15px;
    margin-right: 5px;
    margin-top: 5px;
    width: 46.5vw;
}
.rest-card-buttons {
  width: 100%;
  justify-content: space-evenly;
  display: flex;
  flex-direction: row;
  align-items: center;
}
#recommendation-car, #recommendation-menu, #recommendation-phone {
  font-size: 2rem;
  background: rgba(34, 34, 34, 0.575);
  border: none;
  border-radius: 10px;
  color: rgba(218, 165, 32, 0.876);
  cursor: pointer;
  padding: 5px;
}

#reject-btn, #like-btn {
  font-size: 3rem;
  background: rgba(34, 34, 34, 0.575);
  border: none;
  border-radius: 10px;
  color: rgba(218, 165, 32, 0.876);
  cursor: pointer;
  padding: 5px;
}

.cuisine-checkbox {
  width: 50vw;
}

.fav-btn {
  padding: 5%;
}

.cuisine-button {
    background-color:rgb(88, 87, 87);
    color: rgb(247, 248, 246);
    font-weight: bold;
}
.btn-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  height: auto;
  width: 50vw;
  align-items: center;
  justify-content: center;
  max-width: 500px;
  grid-column-gap: none;
}
.cuisine-checkbox {
  display: flex;
  flex-direction: row;
  align-items: center;
  align-content: center;
  justify-content: center;
  max-width: 500px;
}

#btn:hover {
  
  -webkit-transform: scale(1.2);
  -ms-transform: scale(1.2);
  transform: scale(1.2);
  text-shadow: -1px 1px 2px #000,
	1px 1px 2px #000,
	1px -1px 0 #000,
	-1px -1px 0 #000;
}

::-webkit-input-placeholder {
  font-size: 18px;
}
::-moz-placeholder {
  font-size: 18px;
}
:-ms-input-placeholder {
  font-size: 18px;
}
::placeholder {
  font-size: 18px;
}

@media (max-width: 700px) {
  /* .btn-container {
    display: grid;
    grid-template-columns: 1fr 1fr;
  } */
.cuisine-checkbox {
  width: 100%;
  padding: 5px;
}
.btn-container div button {
  width: 100%;
}
.btn-container {
  width: 100%;
}
.search-box {
  display: flex;
  flex-direction: column;
  align-content: center;
}
}

@media (min-width: 700px) {
  #reject-btn {
  font-size: 5rem;
}
#like-btn {
  font-size: 5rem;
}
.like-reject {
  max-width: 850px;
}
.like-reject a {
  font-size: 5rem;
}
.rest-card {
  width: 90%;
}
#recommendation-car, #recommendation-menu, #recommendation-phone {
  font-size: 4rem;
}
.search-box {
  margin-bottom: 1rem;
  margin-top: -.175rem;
}
.search-box {
  margin-bottom: 1rem;
  margin-top: -.175rem;
  display: flex;
  flex-direction: row;
  justify-content: left;
  align-content: center;
  align-items: center;
  width: 100%;
}
.search-anything{
  display: flex;
  flex-direction: row;
  margin-top: .2rem;
  margin-bottom: .5rem;
  width: auto;
  align-content: center;
  margin-left: 17.1%;
}
.search-anything input {
    padding: 5px;
    border-radius: 15px;
    margin-right: 5px;
    margin-top: 5px;
    width: 21.5vw;
    max-width: 198px;
    
}
}
@media (min-width: 925px) {
.search-box {
  justify-content: left;
  max-width: 1034px;
}
.search-anything {
margin-left: 99.5px;
}
.search-anything input {
  max-width: 220.5px;
}
}


</style>