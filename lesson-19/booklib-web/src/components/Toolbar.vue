<template>
  <div>
    <v-app-bar app flat height="60" color="blue-grey">
      <v-container class="py-0 px-0 px-sm-2 fill-height">
        <router-link :to="{ name: 'index'}" class="d-flex align-center text-decoration-none mr-2">
          <span class="font-weight-black text-uppercase" style="color: white">
            Booklib
          </span>
        </router-link>
      </v-container>
    </v-app-bar>
  </div>
</template>
