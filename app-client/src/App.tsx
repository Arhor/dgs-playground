import { useQuery } from '@apollo/client';
import { graphql } from '~/gql';

const GET_ALL_USERS = graphql(`
    query GetAllUsers {
        users {
            id
            username
            settings
            posts {
                id
                userId
                content
            }
        }
    }
`);

const GET_USER_BY_USERNAME = graphql(`
    query GetUserByUsername($username: String!) {
        user(username: $username) {
            id
            username
            settings
            posts {
                id
                userId
                content
            }
        }
    }
`);

function App() {
    const { loading, error, data } = useQuery(GET_USER_BY_USERNAME, { variables: { username: '' } });

    if (loading) {
        return (
            <p>Loading...</p>
        );
    }
    if (error) {
        return (
            <p>Error : {error.message}</p>
        );
    }
    if (data) {
        return (
            <table>
                <thead>
                    <tr>
                        <th>id</th>
                        <th>username</th>
                        <th>settings</th>
                    </tr>
                </thead>
                <tbody>
                    {data.users.map(({id, username, settings}) => (
                        <tr key={id}>
                            <td>{id}</td>
                            <td>{username}</td>
                            <td>{settings}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        );
    }

    return null;
}

export default App;
