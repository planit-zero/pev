import * as React from 'react';
import { Box, CircularProgress, Typography } from '@mui/material';
import { useGetMaskedImageMutation } from '../../../pev-service/ImageService';
import { IImage } from '../../../pev-interface/IImage';
import { IconPhotoX, IconRefresh } from '@tabler/icons';
import dayjs from 'dayjs';

type ImageElementProps = {
    content: string;
};

const ImageElement = (props: ImageElementProps) => {
    const [open, setOpen] = React.useState<boolean>(false);

    const [getMaskedImage, { data: maskedImage, isLoading, isError }] = useGetMaskedImageMutation();

    React.useEffect(() => {
        callMaskedImage(false);
    }, []);

    const callMaskedImage = (refresh: boolean) => {
        const payload: IImage = {
            url: props.content,
            refresh: refresh
        };

        getMaskedImage(payload);
    };

    const getCreateDate = (url: string) => {
        const strArr = url.split('-');

        if (strArr.length === 3) {
            const date = strArr[0];
            const time = strArr[1];

            return dayjs(`${date}${time}`).format('YYYY-MM-DD HH:mm:ss');
        } else {
            return '';
        }
    };

    return (
        <Box sx={{ cursor: 'pointer', position: 'relative', width: '100%', height: '100%' }}>
            {isLoading && <CircularProgress />}
            {isError && <IconPhotoX size={'large'} />}
            {!isLoading && !isError && maskedImage && (
                <React.Fragment>
                    <Box sx={{ position: 'absolute', bottom: 0, right: 0 }} onClick={() => callMaskedImage(true)}>
                        <Box display={'flex'} alignItems={'center'}>
                            <Typography display={'inline'} sx={{ fontSize: '11px', mr: 1 }}>
                                이미지 처리 일시: {getCreateDate(maskedImage.url)}
                            </Typography>
                            <IconRefresh size={16} />
                        </Box>
                    </Box>
                    <img
                        style={{ width: '100%', height: '100%', objectFit: 'contain' }}
                        src={`https://deview.snuh.org/masked_images/${maskedImage.url}?currentTime=${new Date().getTime()}`}
                        onClick={() => setOpen(true)}
                        alt={'가명화 이미지'}
                    />
                </React.Fragment>
            )}
        </Box>
    );
};

export default ImageElement;
