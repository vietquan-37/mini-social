export interface Comment {
    id: number;
    userId: number;
    username: string;
    userImage: string|null;
    content: string;
    createTime: string;
}