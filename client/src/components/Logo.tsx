import Typography from "@material-ui/core/Typography";
import { useStyles } from "./themeHeader";

export default function Logo() {
  const classes = useStyles();
  return (
    <Typography
      variant="h6"
      color="inherit"
      noWrap
      className={classes.toolbarTitle}
    >
      ここをロゴにする
    </Typography>
  );
}
