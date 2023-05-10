import { StrictMode } from 'react';

import { ApolloClient, InMemoryCache, ApolloProvider } from '@apollo/client';
import { createRoot } from 'react-dom/client';

import App from '~/App';

const client = new ApolloClient({
    uri: '/api/graphql',
    cache: new InMemoryCache(),
});

createRoot(document.getElementById('root') as HTMLElement).render(
    <StrictMode>
        <ApolloProvider client={client}>
            <App />
        </ApolloProvider>
    </StrictMode>
);
