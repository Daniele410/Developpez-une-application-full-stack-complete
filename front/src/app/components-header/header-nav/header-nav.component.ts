import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-nav',
  templateUrl: './header-nav.component.html',
  styleUrls: ['./header-nav.component.scss'],
})
export class HeaderNavComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
    
  }

  onSelectChange(event: any) {
    this.router.navigate([event.target.value]);
  }

}
