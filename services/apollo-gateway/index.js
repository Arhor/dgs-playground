import { ApolloServer } from '@apollo/server';
import { startStandaloneServer } from '@apollo/server/standalone';
import { ApolloGateway, IntrospectAndCompose } from '@apollo/gateway';

const { url } = await startStandaloneServer(
    new ApolloServer({
        gateway: new ApolloGateway({
            supergraphSdl: new IntrospectAndCompose({
                subgraphs: [
                    {
                        name: '',
                        url: '',
                    },
                ],
            }),
        }),
    })
);

console.log(`🚀  Server ready at ${url}`);
