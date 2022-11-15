<template>
  <v-sheet>
    <v-container>
      <h1>New book</h1>
      <v-form
        v-model="form.valid"
        :disabled="loading"
        @submit.prevent="submitForm"
      >
        <v-card>
          <v-card-text>

            <v-text-field
              v-model="form.data.title"
              label="Title"
              counter="255"
              :rules="form.rule.title"
            ></v-text-field>
            <v-select
              v-model="form.data.authorId"
              label="Author"
              :items="authors"
              item-text="name"
              item-value="id"
            ></v-select>
            <v-select
              v-model="form.data.genreId"
              label="Genre"
              :items="genres"
              item-text="title"
              item-value="id"
            ></v-select>

          </v-card-text>
          <v-card-actions class="pt-4 pb-4">
            <v-spacer></v-spacer>
            <v-btn :to="{name: 'index'}">Cancel</v-btn>
            <v-btn color="primary" :disabled="!form.valid" type="submit">Update</v-btn>
          </v-card-actions>
        </v-card>
      </v-form>
    </v-container>
  </v-sheet>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'BookNew',
  head() {
    return {
      title: 'New book'
    }
  },
  async fetch(context) {
    await context.store.dispatch('book/loadNewBook')
  },
  data() {
    return {
      form: {
        valid: true,
        data: {
          title: '',
          authorId: 0,
          genreId: 0
        },
        rule: {
          title: [
            (v) => !!v || 'Title is required'
          ]
        }
      }
    }
  },
  computed: {
    ...mapState('book', ['loading']),
    ...mapState('book', {
      authors: (state) => [{ id: 0, name: 'No author' }
        , ...state.authors
      ],
      genres: (state) => [{ id: 0, title: 'No genre' }
        , ...state.genres
      ]
    })
  },
  methods: {
    submitForm() {
      this.$store.dispatch('book/createBook', {  ... this.form.data })
        .then(() => this.$router.push({ name: 'index' }))
    }
  }
}
</script>

<style scoped>

</style>
