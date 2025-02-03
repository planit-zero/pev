import * as React from 'react';
import { Box, CircularProgress, Divider, IconButton, Modal, Typography } from '@mui/material';
import { useGetMaskedImageMutation } from '../../../pev-service/ImageService';
import { IImage } from '../../../pev-interface/IImage';
import { IconPhotoX, IconRefresh, IconX } from '@tabler/icons';
import dayjs from 'dayjs';

type ImageElementProps = {
    maskingYn: 'Y' | 'N';
    content: string;
};

const ImageElement = (props: ImageElementProps) => {
    const [open, setOpen] = React.useState<boolean>(false);

    const [getMaskedImage, { data: maskedImage, isLoading, isError }] = useGetMaskedImageMutation();

    React.useEffect(() => {
        if (props.maskingYn === 'Y') callMaskedImage(false);
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

    const getUrl = () => {
        if (props.maskingYn === 'Y') {
            if (!maskedImage) return ``;
            return `https://deview.snuh.org/masked_images/${maskedImage.url}`;
        } else {
            // console.log(props.content);
            return `${props.content}`;
        }
    };

    const [size, setSize] = React.useState<{ width: number; height: number }>({ width: 0, height: 0 });

    const imgRef = React.useRef<HTMLImageElement>(null);

    React.useEffect(() => {
        if (open && imgRef && imgRef.current) {
            setSize({
                width: imgRef.current.naturalWidth,
                height: imgRef.current.naturalHeight
            });
        }
    }, [open]);

    return (
        <Box sx={{ cursor: 'pointer', position: 'relative', width: '100%', height: '100%' }}>
            {isLoading && <CircularProgress />}
            {isError && <IconPhotoX size={'large'} />}
            {((props.maskingYn === 'Y' && !isLoading && !isError && maskedImage) || props.maskingYn === 'N') && (
                <React.Fragment>
                    {props.maskingYn === 'Y' && maskedImage && (
                        <Box sx={{ position: 'absolute', bottom: 0, right: 0 }} onClick={() => callMaskedImage(true)}>
                            <Box display={'flex'} alignItems={'center'}>
                                <Typography display={'inline'} sx={{ fontSize: '11px', mr: 1 }}>
                                    이미지 처리 일시: {getCreateDate(maskedImage.url)}
                                </Typography>
                                <IconRefresh size={16} />
                            </Box>
                        </Box>
                    )}
                    <img
                        ref={imgRef}
                        style={{ width: '100%', height: '100%', objectFit: 'contain' }}
                        src={getUrl()}
                        onClick={() => setOpen(true)}
                        alt={'가명화 이미지'}
                    />
                    <Modal open={open} onClose={() => setOpen(false)}>
                        <Box
                            sx={{
                                position: 'absolute',
                                top: '50%',
                                left: '50%',
                                transform: 'translate(-50%, -50%)',
                                width: '90vw',
                                height: '90vh',
                                backgroundColor: '#eef2f6',
                                boxShadow: 24,
                                p: 2
                            }}
                        >
                            <Box
                                sx={{
                                    width: '100%',
                                    height: '30px',
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    alignItems: 'center'
                                }}
                            >
                                <Typography sx={{ fontSize: 'h3.fontSize' }}>이미지 뷰어</Typography>
                                <IconButton onClick={() => setOpen(false)}>
                                    <IconX />
                                </IconButton>
                            </Box>
                            <Divider sx={{ my: 1.5 }} />
                            <Box
                                sx={{
                                    width: '100%',
                                    height: 'calc(100% - 55px)',
                                    overflow: 'scroll'
                                }}
                            >
                                <img src={getUrl()} alt={'가명화 이미지'} width={size.width || '100%'} height={size.height || '100%'} />
                            </Box>
                        </Box>
                    </Modal>
                </React.Fragment>
            )}
        </Box>
    );
};

export default ImageElement;
