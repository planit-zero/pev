import * as React from 'react';
import { Divider, List, ListItem, ListItemButton, ListItemIcon, ListItemText, ListSubheader, Paper } from '@mui/material';
import Check from '@mui/icons-material/Check';
import { ErrorOutline, HourglassTop, AccessTime, Assessment } from '@mui/icons-material';

type ReportMenuProps = {
    menuIndex: number;
    setMenuIndex: (index: number) => void;
};

const ReportMenu = (props: ReportMenuProps) => {
    return (
        <Paper sx={{ width: '20%', height: '100%' }}>
            <List component={'nav'}>
                <ListSubheader component={'div'}>가명처리 오류신고</ListSubheader>
                <ListItem disablePadding={true}>
                    <ListItemButton selected={props.menuIndex === 0} onClick={() => props.setMenuIndex(0)}>
                        <ListItemText primary={'모든 신고 내역'} />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding={true}>
                    <ListItemButton selected={props.menuIndex === 1} onClick={() => props.setMenuIndex(1)}>
                        <ListItemIcon>
                            <Check />
                        </ListItemIcon>
                        <ListItemText primary={'처리된 신고 내역'} />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding={true}>
                    <ListItemButton selected={props.menuIndex === 2} onClick={() => props.setMenuIndex(2)}>
                        <ListItemIcon>
                            <HourglassTop />
                        </ListItemIcon>
                        <ListItemText primary={'처리 중인 신고 내역'} />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding={true}>
                    <ListItemButton selected={props.menuIndex === 3} onClick={() => props.setMenuIndex(3)}>
                        <ListItemIcon>
                            <ErrorOutline />
                        </ListItemIcon>
                        <ListItemText primary={'처리되지 않은 신고 내역'} />
                    </ListItemButton>
                </ListItem>
                <Divider />
                <ListSubheader component={'div'}>기록지 오류신고</ListSubheader>
                <ListItem disablePadding={true}>
                    <ListItemButton selected={props.menuIndex === 4} onClick={() => props.setMenuIndex(4)}>
                        <ListItemText primary={'모든 신고 내역'} />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding={true}>
                    <ListItemButton selected={props.menuIndex === 5} onClick={() => props.setMenuIndex(5)}>
                        <ListItemIcon>
                            <Check />
                        </ListItemIcon>
                        <ListItemText primary={'처리된 신고 내역'} />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding={true}>
                    <ListItemButton selected={props.menuIndex === 6} onClick={() => props.setMenuIndex(6)}>
                        <ListItemIcon>
                            <ErrorOutline />
                        </ListItemIcon>
                        <ListItemText primary={'처리되지 않은 신고 내역'} />
                    </ListItemButton>
                </ListItem>
                <Divider />
                <ListSubheader component={'div'}>사용자 접속</ListSubheader>
                <ListItem disablePadding={true}>
                    <ListItemButton selected={props.menuIndex === 7} onClick={() => props.setMenuIndex(7)}>
                        <ListItemIcon>
                            <AccessTime />
                        </ListItemIcon>
                        <ListItemText primary={'로그'} />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding={true}>
                    <ListItemButton selected={props.menuIndex === 8} onClick={() => props.setMenuIndex(8)}>
                        <ListItemIcon>
                            <Assessment />
                        </ListItemIcon>
                        <ListItemText primary={'통계'} />
                    </ListItemButton>
                </ListItem>
            </List>
        </Paper>
    );
};

export default ReportMenu;
