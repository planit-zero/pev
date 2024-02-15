export const UrlUtils = {
    getHost: (profile: string): string => {
        if (profile === 'local') return 'http://localhost:3000';
        return 'https://deview.snuh.org';
    }
};
