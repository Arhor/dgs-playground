import { useEffect } from 'react';

import { useQuery } from '@apollo/client';
import { useSnackbar } from 'notistack';
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
    const { loading, error, data, previousData } = useQuery(GET_ALL_USERS, { pollInterval: 5_000 });
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        if (error) {
            enqueueSnackbar(error.message, {
                variant: 'error',
                autoHideDuration: 10_000,
            });
        }
    }, [error]);

    if (loading) {
        return (
            <Loading />
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
                    {(data ?? previousData)?.users.map(({ id, username, settings }) => (
                        <TableRow key={id}>
                            <TableCell>{id}</TableCell>
                            <TableCell>{username}</TableCell>
                            <TableCell>{settings?.map(it => it.toLocaleLowerCase().replaceAll('_', ' '))?.join(', ')}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default Home;
