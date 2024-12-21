import { RegisterRequestInterface } from "./RegisterRequestInterface";

export interface UpdateRequestInterface extends RegisterRequestInterface {
    id: string | null | undefined
}