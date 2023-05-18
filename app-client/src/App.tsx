import { ApolloClient, InMemoryCache, ApolloProvider } from '@apollo/client';

import CssBaseline from '@mui/material/CssBaseline';

import AppLayout from '~/AppLayout';
import AppThemeProvider from '~/AppThemeProvider';
import ErrorBoundary from '~/components/ErrorBoundary';

const client = new ApolloClient({
    uri: '/graphql',
    cache: new InMemoryCache(),
});

const App = () => (
    <AppThemeProvider>
        <CssBaseline />
        <ErrorBoundary>
            <ApolloProvider client={client}>
                <AppLayout />
            </ApolloProvider>
        </ErrorBoundary>
    </AppThemeProvider>
);

export default App;
