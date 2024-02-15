import * as React from 'react';

// routing
import Routes from 'routes';

// project imports
import NavigationScroll from 'layout/NavigationScroll';

import ThemeCustomization from 'themes';

// auth provider
import { JWTProvider as AuthProvider } from 'contexts/JWTContext';
import { useGetServerActiveProfileQuery } from './pev-service/EnvService';
import { setProfile } from './store/pev-slices/environment';
// import { FirebaseProvider as AuthProvider } from 'contexts/FirebaseContext';
// import { AWSCognitoProvider as AuthProvider } from 'contexts/AWSCognitoContext';
// import { Auth0Provider as AuthProvider } from 'contexts/Auth0Context';

// ==============================|| APP ||============================== //

const App = () => {
    const { data: profileInfo } = useGetServerActiveProfileQuery();

    React.useEffect(() => {
        if (profileInfo) {
            setProfile(profileInfo.profile);
        }
    }, [profileInfo]);

    return (
        <ThemeCustomization>
            <NavigationScroll>
                <AuthProvider>
                    <>
                        <Routes />
                    </>
                </AuthProvider>
            </NavigationScroll>
        </ThemeCustomization>
    );
};

export default App;
