import Button from "@material-ui/core/Button";
import { useStyles } from "./themeHeader";
import AccountCircleIcon from "@material-ui/icons/AccountCircle";

export default function User() {
  const classes = useStyles();
  return (
    <>
      <Button href="#" color="primary" variant="text" className={classes.link}>
        <AccountCircleIcon
          color="primary"
          fontSize="large"
          className={classes.link}
        />
        User
      </Button>
    </>
  );
}
