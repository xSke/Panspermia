#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform vec3 vHSV;
uniform sampler2D u_texture;

vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main() {
    vec4 textureColor = texture2D(u_texture, v_texCoords);
    vec3 fragRGB = textureColor.rgb;
    vec3 fragHSV = rgb2hsv(fragRGB).xyz;
    fragHSV.x += vHSV.x / 360.0;
    fragHSV.yz *= vHSV.yz;
    // This mod thingy breaks white stuff
    //fragHSV.xyz = mod(fragHSV.xyz, 1.0);
    fragRGB = hsv2rgb(fragHSV);
    gl_FragColor = v_color * vec4(fragRGB, textureColor.w);
}