function x(){}function w(t,n){for(const e in n)t[e]=n[e];return t}function E(t){return t()}function D(){return Object.create(null)}function j(t){t.forEach(E)}function F(t){return typeof t=="function"}function P(t,n){return t!=t?n==n:t!==n||t&&typeof t=="object"||typeof t=="function"}let i;function S(t,n){return t===n?!0:(i||(i=document.createElement("a")),i.href=n,t===i.href)}function U(t){return Object.keys(t).length===0}function q(t,...n){if(t==null){for(const o of n)o(void 0);return x}const e=t.subscribe(...n);return e.unsubscribe?()=>e.unsubscribe():e}function A(t,n,e){t.$$.on_destroy.push(q(n,e))}function B(t,n,e,o){if(t){const r=m(t,n,e,o);return t[0](r)}}function m(t,n,e,o){return t[1]&&o?w(e.ctx.slice(),t[1](o(n))):e.ctx}function C(t,n,e,o){if(t[2]&&o){const r=t[2](o(e));if(n.dirty===void 0)return r;if(typeof r=="object"){const a=[],h=Math.max(n.dirty.length,r.length);for(let s=0;s<h;s+=1)a[s]=n.dirty[s]|r[s];return a}return n.dirty|r}return n.dirty}function G(t,n,e,o,r,a){if(r){const h=m(n,e,o,a);t.p(h,r)}}function H(t){if(t.ctx.length>32){const n=[],e=t.ctx.length/32;for(let o=0;o<e;o++)n[o]=-1;return n}return-1}let f;function _(t){f=t}function b(){if(!f)throw new Error("Function called outside component initialization");return f}function I(t){b().$$.on_mount.push(t)}function J(t){b().$$.after_update.push(t)}function K(t){b().$$.on_destroy.push(t)}function L(t,n){const e=t.$$.callbacks[n.type];e&&e.slice().forEach(o=>o.call(this,n))}const l=[],g=[];let u=[];const y=[],k=Promise.resolve();let p=!1;function v(){p||(p=!0,k.then(z))}function N(){return v(),k}function O(t){u.push(t)}const d=new Set;let c=0;function z(){if(c!==0)return;const t=f;do{try{for(;c<l.length;){const n=l[c];c++,_(n),M(n.$$)}}catch(n){throw l.length=0,c=0,n}for(_(null),l.length=0,c=0;g.length;)g.pop()();for(let n=0;n<u.length;n+=1){const e=u[n];d.has(e)||(d.add(e),e())}u.length=0}while(l.length);for(;y.length;)y.pop()();p=!1,d.clear(),_(t)}function M(t){if(t.fragment!==null){t.update(),j(t.before_update);const n=t.dirty;t.dirty=[-1],t.fragment&&t.fragment.p(t.ctx,n),t.after_update.forEach(O)}}function Q(t){const n=[],e=[];u.forEach(o=>t.indexOf(o)===-1?n.push(o):e.push(o)),e.forEach(o=>o()),u=n}export{B as a,C as b,A as c,K as d,L as e,J as f,H as g,g as h,F as i,S as j,D as k,z as l,U as m,x as n,I as o,O as p,Q as q,j as r,P as s,N as t,G as u,f as v,_ as w,E as x,l as y,v as z};