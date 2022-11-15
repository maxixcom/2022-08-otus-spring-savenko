<template>
  <v-sheet>
    <v-container>
      <h1>Books</h1>
      <div class="my-2 text-right">
        <v-btn color="primary" :to="{name:'book-new'}">
          <v-icon dark>
            mdi-plus
          </v-icon>
          New book
        </v-btn>
      </div>

      <v-card>
        <v-data-table
          :headers="headers"
          :items="books"
          :items-per-page="-1"
          :loading="loading"
          class="elevation-1"
          :dense="false"
          :calculate-widths="false"
          item-key="id"
        >
          <template v-slot:item.actions="{ item }">
            <v-icon
              small
              class="mr-2"
              @click="editItem(item)"
            >
              mdi-pencil
            </v-icon>
            <v-icon
              small
              @click="deleteItem(item)"
            >
              mdi-delete
            </v-icon>
          </template>

        </v-data-table>
      </v-card>
    </v-container>
    <v-dialog v-model="dialogDelete" max-width="500px">
      <v-card>
        <v-card-title class="text-h5">Are you sure you want to delete this book?</v-card-title>
        <v-card-text class="py-2">
          #{{ editedBook?.id }} {{ editedBook?.title }} ({{ editedBook?.author?.name }})
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="closeDelete">Cancel</v-btn>
          <v-btn color="error" text @click="deleteBookConfirm">OK</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-sheet>
</template>

<script>

import { mapState } from 'vuex'

export default {
  name: 'Index',
  head() {
    return {
      title: 'Books'
    }
  },
  components: {},
  async fetch(context) {
    await context.store.dispatch('index/fetchBooks')
  },
  data() {
    return {
      name: 'index',
      headers: [
        {
          text: 'ID',
          align: 'start',
          value: 'id'
        },
        { text: 'Title', value: 'title' },
        { text: 'Author', value: 'author.name' },
        { text: 'Genre', value: 'genre.title' },
        { text: 'Actions', value: 'actions', sortable: false }
      ],
      editedBook: null,
      dialogDelete: false
    }
  },
  computed: {
    ...mapState('index', ['books', 'loading'])
  },
  methods: {
    editItem(item) {
      this.$router.push({ name: 'book-id', params: { id: item.id } })
    },
    deleteItem(item) {
      this.editedBook = item
      this.dialogDelete = true
    },
    closeDelete() {
      this.dialogDelete = false
    },
    deleteBookConfirm() {
      this.$store.dispatch('index/deleteBook', this.editedBook.id)
      this.dialogDelete = false
    }
  }
}
</script>
