import { useTranslation } from 'react-i18next';

import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';

import Loading from '~/components/Loading/Loading';
import useTopics from '~/hooks/useTopics';

const Home = () => {
    const { t } = useTranslation();
    const { loading, data, previousData } = useTopics();


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
                        <TableCell>{t('Name')}</TableCell>
                        <TableCell>{t('Content')}</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {(data ?? previousData)?.topics.map(({ id, name, lastPost }) => (
                        <TableRow key={id}>
                            <TableCell>{id}</TableCell>
                            <TableCell>{name}</TableCell>
                            <TableCell>{lastPost?.content.substring(0, 20)}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default Home;
