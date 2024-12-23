import { RegisterRequestInterface } from "./RegisterRequestInterface";

export interface UpdateProfileRequestInterface extends RegisterRequestInterface {
    id: string | null | undefined
}