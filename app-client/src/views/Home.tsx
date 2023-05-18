import { useQuery } from '@apollo/client';
import { useTranslation } from 'react-i18next';

import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';

import Loading from '~/components/Loading/Loading';
import { graphql } from '~/gql';

const GET_ALL_USERS = graphql(`
    query GetAllUsers {
        users {
            id
            username
            settings
        }
    }
`);

const Home = () => {
    const { t } = useTranslation();
    const { loading, error, data } = useQuery(GET_ALL_USERS, { pollInterval: 30_000 });

    if (loading) {
        return (
            <Loading />
        );
    }
    if (error) {
        return (
            <p>Error : {error.message}</p>
        );
    }

    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>{t('Id')}</TableCell>
                        <TableCell>{t('Username')}</TableCell>
                        <TableCell>{t('Settings')}</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data!.users.map(({ id, username, settings }) => (
                        <TableRow key={id}>
                            <TableCell>{id}</TableCell>
                            <TableCell>{username}</TableCell>
                            <TableCell>{settings}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default Home;
