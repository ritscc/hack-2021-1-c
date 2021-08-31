import { VFC } from "react";
import Link from "next/link";

type Props = {
  title: string;
  link: string;
};

export const HeaderTab: VFC<Props> = ({ title, link }) => (
  <div style={{ marginTop: "" }}>
    <div
      style={{
        fontSize: "1.25rem",
        lineHeight: "1.75rem",
        color: "white",
        marginRight: "2rem",
      }}
    >
      <Link href={link}>{title}</Link>
    </div>
  </div>
);
