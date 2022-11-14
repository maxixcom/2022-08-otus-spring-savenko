export default class Book {
  constructor(data) {
    this.id = data?.id
    this.title = data?.title
    this.authorId = data?.authorId
    this.genreId = data?.genreId
  }

  static empty() {
    return new Book()
  }

  static create(data) {
    return new Book(data)
  }
}
