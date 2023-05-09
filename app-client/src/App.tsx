import { useQuery, gql } from '@apollo/client';

const GET_USERS = gql`
    query GetUsers {
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
`;

type User = {
    id: number,
    username: string,
    settings: string[],
    posts: Post[],
};

type Post = {
    id: number,
    userId: number,
    content: string,
};

type TData = {
    users: User[]
};

function App() {
    const { loading, error, data } = useQuery<TData>(GET_USERS);

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
                    {data.users.map(({ id, username, settings }) => (
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
