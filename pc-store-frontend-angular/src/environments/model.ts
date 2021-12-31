export interface IEnvironment {
  stage: Stages;
}

export enum Stages {
  LOCAL = 'local', DEV = 'development', PROD = 'production'
}
