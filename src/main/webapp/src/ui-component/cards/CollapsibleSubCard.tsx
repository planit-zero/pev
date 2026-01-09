import React, { ReactNode, Ref, useState } from 'react';

// material-ui
import { useTheme } from '@mui/material/styles';
import { Card, CardContent, CardHeader, Divider, Typography, Collapse, IconButton } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

interface CollapsibleSubCardProps {
  children: ReactNode | string | null;
  content?: boolean;
  className?: string;
  contentClass?: string;
  darkTitle?: boolean;
  secondary?: ReactNode | string;
  sx?: {};
  contentSX?: {};
  title?: ReactNode | string;
  defaultExpanded?: boolean;
}

// ==============================|| COLLAPSIBLE SUB CARD ||============================== //

const CollapsibleSubCard = React.forwardRef(
  (
    {
      children,
      className,
      content,
      contentClass,
      darkTitle,
      secondary,
      sx = {},
      contentSX = {},
      title,
      defaultExpanded = true,
      ...others
    }: CollapsibleSubCardProps,
    ref: Ref<HTMLDivElement>
  ) => {
    const theme = useTheme();
    const [expanded, setExpanded] = useState(defaultExpanded);

    const handleExpandClick = () => {
      setExpanded(!expanded);
    };

    return (
      <Card
        ref={ref}
        sx={{
          border: '1px solid',
          borderColor: theme.palette.mode === 'dark' ? theme.palette.dark.light + 15 : theme.palette.grey[200],
          ':hover': {
            boxShadow: theme.palette.mode === 'dark' ? '0 2px 14px 0 rgb(33 150 243 / 10%)' : '0 2px 14px 0 rgb(32 40 45 / 8%)',
          },
          ...sx,
        }}
        {...others}
      >
        {/* card header and action */}
        {!darkTitle && title && (
          <CardHeader
            sx={{ p: 2.5 }}
            title={<Typography variant="h5">{title}</Typography>}
            action={
              <>
                <IconButton
                  onClick={handleExpandClick}
                  aria-expanded={expanded}
                  aria-label="show more"
                  size="small"
                  sx={{
                    transform: expanded ? 'rotate(180deg)' : 'rotate(0deg)',
                    transition: theme.transitions.create('transform', {
                      duration: theme.transitions.duration.shortest,
                    }),
                    marginRight: secondary ? 1 : 0,
                  }}
                >
                  <ExpandMoreIcon />
                </IconButton>
                {secondary}
              </>
            }
          />
        )}
        {darkTitle && title && (
          <CardHeader
            sx={{ p: 2.5 }}
            title={<Typography variant="h4">{title}</Typography>}
            action={
              <>
                <IconButton
                  onClick={handleExpandClick}
                  aria-expanded={expanded}
                  aria-label="show more"
                  size="small"
                  sx={{
                    transform: expanded ? 'rotate(180deg)' : 'rotate(0deg)',
                    transition: theme.transitions.create('transform', {
                      duration: theme.transitions.duration.shortest,
                    }),
                    marginRight: secondary ? 1 : 0,
                  }}
                >
                  <ExpandMoreIcon />
                </IconButton>
                {secondary}
              </>
            }
          />
        )}

        {/* content & header divider */}
        {title && (
          <Divider
            sx={{
              opacity: 1,
              borderColor: theme.palette.mode === 'dark' ? theme.palette.dark.light + 15 : theme.palette.grey[200],
            }}
          />
        )}

        {/* card content with collapse */}
        <Collapse in={expanded} timeout="auto" unmountOnExit>
          {content && (
            <CardContent sx={{ p: 2.5, ...contentSX }} className={contentClass || ''}>
              {children}
            </CardContent>
          )}
          {!content && children}
        </Collapse>
      </Card>
    );
  }
);

CollapsibleSubCard.defaultProps = {
  content: true,
  defaultExpanded: true,
};

export default CollapsibleSubCard;
