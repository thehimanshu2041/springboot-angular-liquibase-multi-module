export class ColorScheme {
  primaryColor: string;
  secondaryColor: string;
  accentColor: string;
  warnColor: string;
  isNightMode: string | boolean;
}

export interface CustomTheme {
  name: string;
  accent: string;
  primary: string;
  isDark?: boolean;
  isDefault?: boolean;
}

export interface Color {
  name: string;
  hex: string;
  darkContrast: boolean;
}

export interface Toc {
  id: number;
  heading: string;
  name: string;
  route: string;
  child: Toc[];
}
