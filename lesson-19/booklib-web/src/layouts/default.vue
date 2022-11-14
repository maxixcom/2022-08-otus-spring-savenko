<template>
  <v-app>
    <v-main>
      <Toolbar></Toolbar>
      <nuxt />
    </v-main>
  </v-app>
</template>
<script>
import Toolbar from '~/components/Toolbar'
export default {
  components: { Toolbar }
}
</script>
