export class Post {
  id: number;
  title: string;
  description: string;
  imageUrl: string;

  constructor(
    id: number = 0,
  title: string = '',
  description: string = '',
  imageUrl: string = ''
  ){
    this.id = id;
    this.title = title;
    this.description = description;
    this.imageUrl = imageUrl;
  }
}
