<template>
 <div class=body-container>
        <div id="background-image"></div>
    <div class="content">
    <div class="rest-card" v-for="restaurant in restaurants" :key="restaurant.id">
        <h2 class="rest-name">{{restaurant.restaurantName}}</h2>
        <a v-bind:href="restaurant.url" target="_blank"><img v-bind:src="restaurant.featuredImage"/></a> 
        <p id="rest-cuisine">Cuisine Type: {{restaurant.cuisine}}</p>
        <p id="rest-ratings">Star Rating: {{restaurant.rating}} / 5</p>
        <div class="menu-bar">
            <span>
                <a id="favorite-phone" href= "tel:parsePhone(restaurant.phoneNumber)"><i class="fa fa-phone"></i></a>
            </span>
            <span>
                <a id="favorite-menu" v-bind:href="restaurant.menuUrl" target="_blank"><i class ="fa fa-book"></i></a>
            </span>
            <span>     
                <a id="remove-fav"><i @click="setCurrentRestId(restaurant.restaurantId)" class="fa fa-trash"></i></a>
            </span>  
        </div>
    </div>
    </div>
 </div>
</template>

<script>
import DBService from '../services/DBService';

export default {
    name: "favorite",

    data() {
        return {
            currentRestId: 0,
            restaurants: [{
            restaurant: {
            }
            }]
        }
    },
    methods: {
        parsePhone(phoneNumber) {
      let helper = 1 + "-" + phoneNumber.replace("(", " ").replace(")", "-");
      return helper;
    },
        getAllFavorites() {
            DBService.getAllFavorites(this.$store.state.user.id).then(response => {
                this.restaurants = response.data;
                })            
            },
        deleteFavorite() {            
            DBService.deleteFavorite(this.$store.state.user.id, this.currentRestId).then(response => {
                if(response.status == 204) {
                    alert('Favorite Removed (Please Refresh The Page)');                                       
                } 
            })            
        },
        setCurrentRestId(restId) {
            this.currentRestId = restId;            
            this.deleteFavorite(); 
            location.reload();           
        }       
    },
        created() {
            this.getAllFavorites();
        }
    }

</script>

<style>

.body-container {
    display: flex;
    flex-direction: column;
    justify-items: center;
    background-image: url('../assets/background3.jpeg');
    background-size: cover;
    background-attachment: fixed;
    background-repeat: no-repeat;
}
/* #background-image {
  width: 100%;
  height: 100%;
  z-index: 0;
} */
.content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.rest-card {
    background-color:rgb(41, 42, 43);
}
.rest-card img {
    width: 80%;
}
#favorite-phone, #favorite-menu, #remove-fav {
    color: rgba(218, 20, 20, 0.733);
    font-size: 3rem;
    
}
.menu-bar {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    width: 100%;
    margin-top: 7.5px;
    align-items: center;
}
.menu-bar span:hover {
     -webkit-transform: scale(1.2);
  -ms-transform: scale(1.2);
  transform: scale(1.2);
  text-shadow: -1px 1px 2px #000,
	1px 1px 2px #000,
	1px -1px 0 #000,
	-1px -1px 0 #000;
}
.content {
  position: center;
  left: 0;
  right: 0;
  z-index: 0;
  margin-left: 20px;
  margin-right: 20px;
}

</style>