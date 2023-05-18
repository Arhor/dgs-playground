import { useState } from 'react';

import { Link as RouterLink } from 'react-router-dom';

import GroupIcon from '@mui/icons-material/Group';
import UserAddIcon from '@mui/icons-material/PersonAdd';
import PostAddIcon from '@mui/icons-material/PostAdd';
import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import Paper from '@mui/material/Paper';

const Footer = () => {
    const [value, setValue] = useState(0);

    return (
        <Paper sx={{ position: 'fixed', bottom: 0, left: 0, right: 0 }} elevation={3}>
            <BottomNavigation value={value} onChange={(e, newValue) => { setValue(newValue); }}>
                <BottomNavigationAction
                    icon={<GroupIcon />}
                    component={RouterLink}
                    to="/"
                />
                <BottomNavigationAction
                    icon={<UserAddIcon />}
                    component={RouterLink}
                    to="/create-user"
                />
                <BottomNavigationAction
                    icon={<PostAddIcon />}
                    component={RouterLink}
                    to="/create-post"
                />
            </BottomNavigation>
        </Paper>
    );
};

export default Footer;
