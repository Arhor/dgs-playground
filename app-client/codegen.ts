import type { CodegenConfig } from '@graphql-codegen/cli';

const config: CodegenConfig = {
    overwrite: true,
    schema: [
        '../app-server/src/main/resources/schema/'
    ],
    documents: [
        'src/**/*.{ts,tsx}',
    ],
    generates: {
        'src/gql/': {
            preset: 'client',
        },
    },
};

export default config;
