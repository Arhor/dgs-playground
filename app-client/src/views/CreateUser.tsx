import { FormEvent } from 'react';

import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';

import Loading from '~/components/Loading/Loading';
import useAvailableUserSettings from '~/hooks/useAvailableUserSettings';
import useCreateUserMutation from '~/hooks/useCreateUserMutation';
import { Optional } from '~/utils/core-utils';

const CreateUser = () => {
    const { loading, availableUserSettings, switchSetting } = useAvailableUserSettings();
    const { createUser } = useCreateUserMutation();

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const formData = new FormData(e.currentTarget);

        const username = formData.get('username') as Optional<string>;
        const settings = availableUserSettings.filter(it => it.checked).map(it => it.name);

        if (username) {
            await createUser({
                variables: {
                    username,
                    settings,
                }
            });
        }
    };

    if (loading) {
        return (
            <Loading />
        );
    }

    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                height: '100vh',
            }}
        >
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
                Sign up
            </Typography>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                <Grid container justifyContent="center">
                    <Grid item xs={10}>
                        <TextField
                            id="username"
                            name="username"
                            label="Username"
                            margin="normal"
                            required
                            fullWidth
                            sx={{ mb: 5 }}
                        />
                    </Grid>
                    <Grid item xs={10}>
                        {availableUserSettings.map(({ name, checked }) => (
                            <FormControlLabel
                                key={name}
                                label={name}
                                control={
                                    <Checkbox
                                        checked={checked}
                                        onChange={() => switchSetting(name)}
                                    />
                                }
                            />
                        ))}
                    </Grid>
                    <Grid item xs={10}>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                        >
                            {"Sign Up"}
                        </Button>
                    </Grid>
                </Grid>
            </Box>
        </Box>
    );
};

export default CreateUser;
