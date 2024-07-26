export interface Post {
    userId: number;
    postId: number;
    content: string | null;
    imageUrl: string | null;
    username: string;
    userImage: string;
    publishDate: string;
}