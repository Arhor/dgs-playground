import { useQuery } from '@apollo/client';

import CreateUserForm from '~/components/CreateUserForm';
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

function App() {
    const { loading, error, data } = useQuery(GET_ALL_USERS);

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
            <div>
                <CreateUserForm />
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
            </div>
        );
    }

    return null;
}

export default App;
