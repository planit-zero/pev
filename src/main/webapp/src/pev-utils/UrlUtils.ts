export const UrlUtils = {
    getHost: (profile: string): string => {
        if (profile === 'local') return 'http://localhost:3000';
        return 'https://deview.snuh.org';
    },
    getIdpUrl: (profile: string): string => {
        if (profile === 'local') return 'http://172.26.33.22:18020?destination=deview_local';
        return 'http://172.26.33.22:18020?destination=deview';
    }
};
