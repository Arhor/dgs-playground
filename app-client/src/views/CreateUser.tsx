import { FormEvent, useState } from 'react';

import { useMutation, useQuery } from '@apollo/client';

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
import { graphql } from '~/gql';
import { Optional } from '~/utils/core-utils';

const GET_AVAILABLE_USER_SETTINGS = graphql(`
    query GetAvailableUserSettings {
        availableUserSettings
    }
`);

const CREATE_USER = graphql(`
    mutation CreateUser($username: String!, $settings: Settings) {
        createUser(request: { username: $username, settings: $settings }) {
            id
            username
            settings
        }
    }
`);

const USER_FRAGMENT = graphql(`
    fragment NewUser on User {
        id
        username
        settings
    }
`);

const CreateUser = () => {
    
    const { loading, error, data } = useQuery(GET_AVAILABLE_USER_SETTINGS/* , {
        onCompleted(data) {

        },
    } */);
    const [createUser] = useMutation(CREATE_USER, {
        update(cache, result) {
            cache.modify({
                fields: {
                    users(existingUsers = []) {
                        return [
                            ...existingUsers,
                            cache.writeFragment({
                                data: result.data?.createUser,
                                fragment: USER_FRAGMENT,
                            }),
                        ];
                    },
                },
            });
        },
    });

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const formData = new FormData(e.currentTarget);

        /* eslint-disable no-console */
        console.log('-------------------------------');
        console.log(formData);
        console.log('-------------------------------');
        /* eslint-enable no-console */

        const username = formData.get('username') as Optional<string>;
        const settings = formData.get('settings') as Optional<string[]>;

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
                        {data!.availableUserSettings.map(setting => (
                            <FormControlLabel key={setting} control={<Checkbox onChange={(e) => {
                                /* eslint-disable no-console */
                                console.log('-------------------------------');
                                console.log(e);
                                console.log('-------------------------------');
                                /* eslint-enable no-console */
                            }} />} label={setting} />
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
