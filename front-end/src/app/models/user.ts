// user.interface.ts
export interface User {
    id: number;
    name: string;
    avatarUrl: string;
    email: string;
    password: string | null; // Password can be null based on the backend response
    phone: string | null; // Phone can be null based on the backend response
  }
  